package com.corsoft.services.internal.screen.timer

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.TimerStateEnum
import com.corsoft.services.internal.model.timer.ShotModel
import com.corsoft.services.internal.utils.Timer

@Immutable
internal data class TimerState(
    val timerState: TimerStateEnum = TimerStateEnum.STOPPED,
    val time: Int = 0,
    val realTime: Int = 0,
    val shotTimes: List<ShotModel> = emptyList(),
    val readyTimerValue: Int = 0,
) : MviState {
    @SuppressLint("DefaultLocale")
    fun formatTime(milliseconds: Int): String {
        val totalSeconds = milliseconds / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        val millis = milliseconds % 1000
        return String.format("%02d:%02d.%03d", minutes, seconds, millis)
    }
}

internal sealed interface TimerAction : MviAction {
    data object StartTimer : TimerAction
    data object StopTimer : TimerAction
    data object StartCountDown : TimerAction
    data object StartRecording : TimerAction
    data class DeleteTime(val index: Int) : TimerAction
}

internal sealed interface TimerEffect : MviEffect {

}