package com.corsoft.services.internal.screen.timer

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreRawRes
import com.corsoft.services.internal.component.enum.TimerStateEnum
import com.corsoft.services.internal.model.timer.ShotModel
import com.corsoft.services.internal.utils.HFCountDownTimer
import com.corsoft.services.internal.utils.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import kotlin.random.Random

internal class TimerViewModel(
    context: Context,
    analytics: AnalyticsRepository,
    val userRepository: UserRepository
) : MviViewModel<TimerState, TimerAction, TimerEffect>(
    TimerState()
) {
    private var timer: Timer = Timer()

    private var updateTime: Job = Job()

    private val mediaPlayer = MediaPlayer.create(context, CoreRawRes.beep_sound)

    private val sampleRate = 44100
    private val bufferSize = AudioRecord.getMinBufferSize(
        sampleRate,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_FLOAT
    )
    private var recording: Job? = null

    init {
        analytics.sendEvent(FirebaseEventsEnum.OPEN_TIMER.key)
        viewModelScope.launch {
            val timerSensitivity = userRepository.getTimerSensitivity()
            changeState {
                it.copy(
                    sensitivity = timerSensitivity
                )
            }
        }
    }

    override fun onAction(action: TimerAction) {
        when (action) {
            is TimerAction.StopTimer -> stopTimer()
            is TimerAction.StartTimer -> startTimer()
            is TimerAction.StartCountDown -> startCountdown()
            is TimerAction.StartRecording -> startListening()
            is TimerAction.DeleteTime -> deleteTime(action.index)
            is TimerAction.ChangeSensitivity -> updateSensitivity(action.sensitivity)
        }
    }

    private fun updateSensitivity(sensitivity: Int) {
        viewModelScope.launch {
            changeState {
                it.copy(
                    sensitivity = sensitivity
                )
            }
            userRepository.setTimerSensitivity(sensitivity)
        }
    }

    private fun startCountdown() {
        viewModelScope.launch {
            changeState { state ->
                state.copy(
                    timerState = TimerStateEnum.WAITING,
                    shotTimes = emptyList(),
                    time = 0,
                    realTime = 0
                )
            }
            HFCountDownTimer(
                Random.nextInt(3, 7) * 1000,
                1000,
                onTimerTick = {
                    changeState { state ->
                        state.copy(readyTimerValue = it.toInt() / 1000 + 1)
                    }
                },
                onTimerFinish = {
                    playBeep()
                    startTimer()
                }
            ).start()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            timer = Timer()
            updateTime = CoroutineScope(Dispatchers.IO).launch {
                updateTimeTask()
            }
            changeState { state ->
                state.copy(timerState = TimerStateEnum.RUN)
            }
        }
    }

    private fun stopTimer() {
        viewModelScope.launch {
            updateTime.cancel()
            changeState { state ->
                state.copy(
                    timerState = TimerStateEnum.STOPPED
                )
            }
        }
    }

    private fun updateTimeTask() {
        while (true) {
            changeState { state ->
                state.copy(realTime = timer.getTimeInMillis())
            }
        }
    }

    private fun playBeep() {
        mediaPlayer.start()
    }

    @SuppressLint("MissingPermission")
    private fun startListening() {
        if (recording == null) {
            recording = CoroutineScope(Dispatchers.IO).launch {

                val audioRecord = AudioRecord(
                    MediaRecorder.AudioSource.UNPROCESSED,
                    sampleRate,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_FLOAT,
                    bufferSize
                )

                audioRecord.startRecording()
                val buffer = FloatArray(bufferSize)

                while (true) {
                    val sensitivity = uiState.value.sensitivity * (1f / 100f)
                    val read = audioRecord.read(buffer, 0, bufferSize, AudioRecord.READ_BLOCKING)
                    if (read > 0) {
                        val maxAmplitude = buffer.maxOrNull() ?: 0f
                        val rms = sqrt(buffer.map { it * it }.average())
                        if (maxAmplitude > 0.5f) {
                            if (rms > sensitivity) {
                                Log.d("REAL_SHOT", maxAmplitude.toString())
                                Log.d("REAL_SHOT_RMS", rms.toString())
                                Log.d("REAL_SHOT_SENS", sensitivity.toString())
                                onShotDetected((maxAmplitude * 32767).toInt())
                            } else {
                                Log.d("SHOT", maxAmplitude.toString())
                                Log.d("SHOT_RMS", rms.toString())
                                Log.d("SHOT_SENS", sensitivity.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun stopListening() {
        recording?.cancel()
    }

    private fun onShotDetected(amp: Int) {
        if (uiState.value.timerState == TimerStateEnum.RUN) {
            changeState { state ->
                val time = timer.getTimeInMillis()
                val times = state.shotTimes.toMutableList()
                times.add(
                    ShotModel(
                        time = time,
                        split = (if (state.shotTimes.isEmpty()) time else time - state.shotTimes.last().time)
                    )
                )
                state.copy(
                    shotTimes = times,
                    time = times.lastOrNull()?.time ?: 0
                )
            }
        }
    }

    private fun deleteTime(index: Int) {
        viewModelScope.launch {
            changeState { state ->
                val newShotTimes = state.shotTimes.toMutableList()
                newShotTimes.removeAt(index)
                state.copy(
                    shotTimes = newShotTimes.toList()
                )
            }
            refreshShotList()
        }
    }

    private fun refreshShotList() {
        val newList = mutableListOf<ShotModel>()
        uiState.value.shotTimes.forEach { shotTime ->
            val currentIndex = uiState.value.shotTimes.indexOf(shotTime)
            newList.add(
                ShotModel(
                    time = shotTime.time,
                    split = if (currentIndex == 0) shotTime.time else shotTime.time - newList[currentIndex - 1].time
                )
            )
        }
        changeState { state ->
            state.copy(
                shotTimes = newList.toList(),
                time = if (newList.isEmpty()) 0 else newList.last().time
            )
        }
    }

}