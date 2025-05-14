package com.corsoft.services.internal.screen.timer

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.TimerStateEnum
import com.corsoft.services.internal.model.timer.ShotModel

@Immutable
internal data class TimerState(
    val timerState: TimerStateEnum = TimerStateEnum.STOPPED,
    val time: Int = 0,
    val realTime: Int = 0,
    val shotTimes: List<ShotModel> = emptyList(),
    val readyTimerValue: Int = 0,
) : MviState

internal sealed interface TimerAction : MviAction {
    data object StartTimer : TimerAction
    data object StopTimer : TimerAction
    data object StartCountDown : TimerAction
    data object StartRecording : TimerAction
    data class DeleteTime(val index: Int) : TimerAction
}

internal sealed interface TimerEffect : MviEffect {

}