package com.corsoft.services.internal.screen.add_weapon

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.services.internal.component.enum.GunTypeEnum

@Immutable
internal data class AddWeaponScreenState(
    val name: String = "",
    val caliber: String = "",
    val serialNumber: String = "",
    val gunType: GunTypeEnum = GunTypeEnum.PISTOL,
) : MviState

internal sealed interface AddWeaponAction : MviAction {
    data class ChangeName(val name: String) : AddWeaponAction
    data class ChangeCaliber(val caliber: String) : AddWeaponAction
    data class ChangeSerialNumber(val serialNumber: String) : AddWeaponAction
    data class ChangeGunType(val gunType: String) : AddWeaponAction
    data object AddWeapon : AddWeaponAction
}

internal sealed interface AddWeaponEffect : MviEffect {
    data class ShowError(val message: String) : AddWeaponEffect
    data object Back : AddWeaponEffect
}