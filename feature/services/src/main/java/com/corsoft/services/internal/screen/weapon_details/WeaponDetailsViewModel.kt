package com.corsoft.services.internal.screen.weapon_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import com.ramcosta.composedestinations.generated.services.destinations.WeaponDetailsScreenDestination
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class WeaponDetailsViewModel(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<WeaponDetailsScreenState, WeaponDetailsAction, WeaponDetailsEffect>(
    WeaponDetailsScreenState()
) {

    private val navArgs = WeaponDetailsScreenDestination.argsFrom(savedStateHandle)

    override fun onAction(action: WeaponDetailsAction) {
        when(action){
            WeaponDetailsAction.Delete -> delete()
        }
    }

    init {
        viewModelScope.launch {
            userRepository.getGunById(navArgs.gunId).doOn(
                success = {
                    changeState { state ->
                        Log.d("WEAPON", it.toString())
                        state.copy(gunModel = it.toUiModel())
                    }
                },
                failed = { }
            )
            setLoading(false)
        }
    }

    private fun delete(){
        viewModelScope.launch {
            userRepository.deleteGunById(uiState.value.gunModel.id).doOn(
                success = {
                    sendEffect(WeaponDetailsEffect.Back)
                },
                failed = {
                    //TODO: add warning
                }
            )
        }
    }

    private fun setLoading(state: Boolean){
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}