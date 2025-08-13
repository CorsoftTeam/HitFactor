package com.corsoft.services.internal.screen.add_training

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel
import java.time.LocalDateTime
import java.util.stream.IntStream.IntMapMultiConsumer

@Immutable
internal data class AddTrainingScreenState(
    val dateTime: LocalDateTime? = null,
    val length: Int? = null,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String? = null,
    val shotCount: Int = 0,
    val userGuns: List<GunModel> = emptyList(),
    val isDialogVisible: Boolean = false
) : MviState

internal sealed interface AddTrainingAction : MviAction {
    data class ChangeDateTime(val dateTime: LocalDateTime): AddTrainingAction
    data class ChangeLength(val length: Int): AddTrainingAction
    data class ChangeHfScore(val hfScore: Float): AddTrainingAction
    data class ChangeNote(val note: String): AddTrainingAction
    data class ChangeWeaponId(val weaponId: String): AddTrainingAction
    data class ChangeShotCount(val shotCount: Int): AddTrainingAction

    data object ShowDialog: AddTrainingAction
    data object HideDialog: AddTrainingAction

    data object AddTraining : AddTrainingAction
}

internal sealed interface AddTrainingEffect : MviEffect {
    data class ShowError(val message: String) : AddTrainingEffect
    data object Back : AddTrainingEffect
}