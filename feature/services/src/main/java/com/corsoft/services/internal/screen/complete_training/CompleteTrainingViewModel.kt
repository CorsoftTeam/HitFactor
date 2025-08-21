package com.corsoft.services.internal.screen.complete_training

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreStringRes
import com.ramcosta.composedestinations.generated.services.destinations.CompleteTrainingScreenDestination
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class CompleteTrainingViewModel(
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider,
    savedStateHandle: SavedStateHandle
) : MviViewModel<CompleteTrainingScreenState, CompleteTrainingAction, CompleteTrainingEffect>(
    CompleteTrainingScreenState()
) {

    val navArgs = CompleteTrainingScreenDestination.argsFrom(savedStateHandle)

    override fun onAction(action: CompleteTrainingAction) {
        when (action) {
            CompleteTrainingAction.CompleteTraining -> completeTraining()
            is CompleteTrainingAction.ChangeHfScore -> changeHfScore(action.hfScore)
            is CompleteTrainingAction.ChangeNote -> changeNote(action.note)
            is CompleteTrainingAction.ChangeShotCount -> changeShotCount(action.shotCount)
        }
    }

    private fun completeTraining() { //TODO: add loading
        viewModelScope.launch {
            val newHfScore = uiState.value.hfScore.toFloatOrNull()
            if (newHfScore == null) {
                sendEffect(CompleteTrainingEffect.ShowError(resourceProvider.getString(CoreStringRes.wrong_hf_field)))
                return@launch
            }
            userRepository.completeTraining(
                id = navArgs.trainingId,
                hfScore = newHfScore,
                shotCount = uiState.value.shotCount,
                note = uiState.value.note
            ).doOn(
                success = {
                    userRepository.getTrainingById(navArgs.trainingId).doOn(
                        success = { response ->
                            userRepository.updateShotCount(response.weaponId, uiState.value.shotCount).doOn(
                                success = {
                                    sendEffect(CompleteTrainingEffect.Back)
                                },
                                failed = { failedResponse ->
                                    sendEffect(CompleteTrainingEffect.ShowError(failedResponse.getErrorMessage()))
                                }
                            )
                        },
                        failed = { response ->
                            sendEffect(CompleteTrainingEffect.ShowError(response.getErrorMessage()))
                        }
                    )
                },
                failed = { response ->
                    sendEffect(CompleteTrainingEffect.ShowError(response.getErrorMessage()))
                }
            )
        }
    }

    private fun changeHfScore(hfScore: String) {
        viewModelScope.launch {
            changeState {
                it.copy(hfScore = hfScore.replace(',', '.'))
            }
        }
    }

    private fun changeNote(note: String) {
        viewModelScope.launch {
            changeState {
                it.copy(note = note)
            }
        }
    }

    private fun changeShotCount(shotCount: Int) {
        viewModelScope.launch {
            changeState {
                it.copy(shotCount = shotCount)
            }
        }
    }
}