package com.corsoft.services.internal.screen.training_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import com.ramcosta.composedestinations.generated.services.destinations.TrainingDetailsScreenDestination
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class TrainingDetailsViewModel(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : MviViewModel<TrainingDetailsScreenState, TrainingDetailsAction, TrainingDetailsEffect>(
    TrainingDetailsScreenState()
) {

    private val navArgs = TrainingDetailsScreenDestination.argsFrom(savedStateHandle)

    override fun onAction(action: TrainingDetailsAction) {
        when (action) {
            TrainingDetailsAction.Delete -> delete()
        }
    }

    init {
        viewModelScope.launch {
            userRepository.getTrainingById(navArgs.trainingId).doOn(
                success = {
                    changeState { state ->
                        state.copy(trainingModel = it.toUiModel())
                    }
                    if (it.weaponId.isNotEmpty()){
                        userRepository.getGunById(it.weaponId).doOn(
                            success = { response ->
                                changeState { state ->
                                    state.copy(usedGun = response.toUiModel())
                                }
                            },
                            failed = {
                                //TODO: errors
                            }
                        )
                    }
                },
                failed = {
                    //TODO: errors
                }
            )
            setLoading(false)
        }
    }

    private fun delete() {
        viewModelScope.launch {
            // TODO: add delete
        }
    }

    private fun setLoading(state: Boolean) {
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}