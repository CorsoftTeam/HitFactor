package com.corsoft.services.internal.screen.add_complex

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.ClickPriceEnum

@Immutable
internal data class AddComplexScreenState(
    val name: String = "",
    // gun
    val gunId: String = "",
    val zeroRange: Int = 0,
    // sight
    val sightHeight: String = "",
    val clickPrice: ClickPriceEnum = ClickPriceEnum.MOA14,
    // shell
    val muzzleVelocity: Int = 0,
    val ballisticCoefficient: String = ""
) : MviState

internal sealed interface AddComplexAction : MviAction {
    data class ChangeName(val name: String) : AddComplexAction
    data class ChangeGunId(val gunId: String) : AddComplexAction
    data class ChangeZeroRange(val zeroRange: String) : AddComplexAction
    data class ChangeSightHeight(val sightHeight: String) : AddComplexAction
    data class ChangeClickPrice(val clickPrice: String) : AddComplexAction
    data class ChangeMuzzleVelocity(val muzzleVelocity: String) : AddComplexAction
    data class ChangeBallisticCoefficient(val ballisticCoefficient: String) : AddComplexAction
    data object AddComplex : AddComplexAction
}

internal sealed interface AddComplexEffect : MviEffect {
    data class ShowError(val message: String) : AddComplexEffect
    data object Back : AddComplexEffect
}