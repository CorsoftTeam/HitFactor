package com.corsoft.services.internal.screen.complete_training

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
internal data class CompleteTrainingScreenState(
    val hfScore: String = "",
    val note: String = "",
    val shotCount: Int = 0,
) : MviState

internal sealed interface CompleteTrainingAction : MviAction {
    data class ChangeHfScore(val hfScore: String) : CompleteTrainingAction
    data class ChangeNote(val note: String) : CompleteTrainingAction
    data class ChangeShotCount(val shotCount: Int) : CompleteTrainingAction

    data object CompleteTraining : CompleteTrainingAction
}

internal sealed interface CompleteTrainingEffect : MviEffect {
    data class ShowError(val message: String) : CompleteTrainingEffect
    data object Back : CompleteTrainingEffect
}