package com.corsoft.services.internal.screen.add_weapon

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.internal.component.enum.GunTypeEnum
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class AddWeaponViewModel(
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider,
    analyticsRepository: AnalyticsRepository
) : MviViewModel<AddWeaponScreenState, AddWeaponAction, AddWeaponEffect>(
    AddWeaponScreenState()
) {
    override fun onAction(action: AddWeaponAction) {
        when (action) {
            is AddWeaponAction.ChangeCaliber -> changeCaliber(action.caliber)
            is AddWeaponAction.ChangeGunType -> changeType(action.gunType)
            is AddWeaponAction.ChangeName -> changeName(action.name)
            is AddWeaponAction.ChangeSerialNumber -> changeSerialNumber(action.serialNumber)
            AddWeaponAction.AddWeapon -> addWeapon()
            is AddWeaponAction.ChangeShotBeforeCleanCount -> changeShotsBeforeCLean(action.shotCount)
        }
    }

    init {
        viewModelScope.launch {
            analyticsRepository.sendEvent(FirebaseEventsEnum.ADD_GUN.key)
        }
    }

    private fun addWeapon() {
        if (
            uiState.value.name == "" ||
            uiState.value.caliber == "" ||
            uiState.value.serialNumber == ""
        ) {
            sendEffect(AddWeaponEffect.ShowError(resourceProvider.getString(CoreStringRes.all_fields_must_be_filled)))
        } else {
            viewModelScope.launch {
                userRepository.addGun(
                    name = uiState.value.name,
                    caliber = uiState.value.caliber,
                    type = uiState.value.gunType.getKey(),
                    serialNumber = uiState.value.serialNumber,
                    shotsBeforeClean = uiState.value.shotsBeforeClean
                ).doOn(
                    success = {
                        sendEffect(AddWeaponEffect.Back)
                    },
                    failed = { response ->
                        sendEffect(AddWeaponEffect.ShowError(response.getErrorMessage()))
                    }
                )
            }
        }
    }

    private fun changeName(name: String) {
        viewModelScope.launch {
            changeState {
                it.copy(name = name)
            }
        }
    }

    private fun changeSerialNumber(serialNumber: String) {
        viewModelScope.launch {
            changeState {
                it.copy(serialNumber = serialNumber)
            }
        }
    }

    private fun changeCaliber(caliber: String) {
        viewModelScope.launch {
            changeState {
                it.copy(caliber = caliber)
            }
        }
    }

    private fun changeType(type: String) {
        viewModelScope.launch {
            changeState {
                it.copy(gunType = GunTypeEnum.fromName(type))
            }
        }
    }

    private fun changeShotsBeforeCLean(shotCount: Int) {
        viewModelScope.launch {
            changeState {
                it.copy(shotsBeforeClean = shotCount)
            }
        }
    }

}