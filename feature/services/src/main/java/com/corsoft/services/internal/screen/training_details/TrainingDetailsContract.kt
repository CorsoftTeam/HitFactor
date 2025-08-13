package com.corsoft.services.internal.screen.training_details

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.model.TrainingModel
import java.time.format.DateTimeFormatter

@Immutable
internal data class TrainingDetailsScreenState(
    val trainingModel: TrainingModel = TrainingModel(),
    val usedGun: GunModel? = null,
    val isLoading: Boolean = true
) : MviState {
    val formattedDate: String
        get() = trainingModel.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    val formattedDateTime: String
        get() = trainingModel.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
}

internal sealed interface TrainingDetailsAction : MviAction {
    data object Delete : TrainingDetailsAction
}

internal sealed interface TrainingDetailsEffect : MviEffect {
    data object Back : TrainingDetailsEffect
}