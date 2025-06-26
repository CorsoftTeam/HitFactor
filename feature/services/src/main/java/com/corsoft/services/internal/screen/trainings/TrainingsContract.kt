package com.corsoft.services.internal.screen.trainings

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.ServiceModel
import com.corsoft.services.internal.model.TrainingModel

@Immutable
internal data class TrainingsScreenState(
    val trainings: List<TrainingModel> = listOf(),
    val isLoading: Boolean = true
) : MviState

internal sealed interface TrainingsAction : MviAction

internal sealed interface TrainingsEffect : MviEffect