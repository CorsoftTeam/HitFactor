package com.corsoft.services.internal.screen.weapon_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import com.ramcosta.composedestinations.generated.services.destinations.WeaponDetailsScreenDestination
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class WeaponDetailsViewModel(
    userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<WeaponDetailsScreenState, WeaponDetailsAction, WeaponDetailsEffect>(
    WeaponDetailsScreenState()
) {

    private val navArgs = WeaponDetailsScreenDestination.argsFrom(savedStateHandle)

    override fun onAction(action: WeaponDetailsAction) {

    }

    init {
        viewModelScope.launch {
            userRepository.getGunById(navArgs.gunId).doOn(
                success = {
                    changeState { state ->
                        state.copy(gunModel = it.toUiModel())
                    }
                },
                failed = { }
            )
        }
    }

}