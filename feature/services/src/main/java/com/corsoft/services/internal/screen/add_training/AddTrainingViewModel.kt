package com.corsoft.services.internal.screen.add_training

import androidx.lifecycle.viewModelScope
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn
import java.time.LocalDateTime

internal class AddTrainingViewModel(
    private val userRepository: UserRepository
) : MviViewModel<AddTrainingScreenState, AddTrainingAction, AddTrainingEffect>(
    AddTrainingScreenState()
) {
    override fun onAction(action: AddTrainingAction) {
        when (action) {
            AddTrainingAction.AddTraining -> addTraining()
            is AddTrainingAction.ChangeDateTime -> changeDateTime(action.dateTime)
            is AddTrainingAction.ChangeHfScore -> changeHfScore(action.hfScore)
            is AddTrainingAction.ChangeLength -> changeLength(action.length)
            is AddTrainingAction.ChangeNote -> changeNote(action.note)
            is AddTrainingAction.ChangeShotCount -> changeShotCount(action.shotCount)
            is AddTrainingAction.ChangeWeaponId -> changeWeaponId(action.weaponId)
            AddTrainingAction.HideDialog -> setDialogState(false)
            AddTrainingAction.ShowDialog -> setDialogState(true)
        }
    }

    init { //TODO: add loading
        viewModelScope.launch {
            userRepository.getMyGuns().doOn(
                success = { response ->
                    changeState { state ->
                        state.copy(
                            userGuns = response.map { it.toUiModel() }
                        )
                    }
                },
                failed = {
                    //TODO: add error
                }
            )
        }
    }

    private fun setDialogState(isVisible: Boolean) {
        viewModelScope.launch {
            changeState {
                it.copy(isDialogVisible = isVisible)
            }
        }
    }

    private fun addTraining() {
        viewModelScope.launch {
            userRepository.addTraining(
                dateTime = uiState.value.dateTime ?: LocalDateTime.now(),
                length = uiState.value.length ?: 0,
                weaponId = uiState.value.weaponId ?: ""
            ).doOn(
                success = {
                    sendEffect(AddTrainingEffect.Back)
                },
                failed = { response ->
                    sendEffect(AddTrainingEffect.ShowError(response.getErrorMessage()))
                }
            )
        }
    }

    private fun changeDateTime(dateTime: LocalDateTime) {
        viewModelScope.launch {
            changeState {
                it.copy(dateTime = dateTime)
            }
        }
    }

    private fun changeLength(length: Int) {
        viewModelScope.launch {
            changeState {
                it.copy(length = length)
            }
        }
    }

    private fun changeHfScore(hfScore: Float) {
        viewModelScope.launch {
            changeState {
                it.copy(hfScore = hfScore)
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

    private fun changeWeaponId(weaponId: String) {
        viewModelScope.launch {
            changeState { state ->
                state.copy(weaponId = state.userGuns.first { it.name == weaponId }.id)
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