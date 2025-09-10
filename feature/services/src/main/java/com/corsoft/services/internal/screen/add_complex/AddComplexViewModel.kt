package com.corsoft.services.internal.screen.add_complex

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.internal.component.enum.ClickPriceEnum
import kotlinx.coroutines.launch

internal class AddComplexViewModel(
    private val userRepository: UserRepository,
    private val resourceProvider: ResourceProvider,
    analyticsRepository: AnalyticsRepository
) : MviViewModel<AddComplexScreenState, AddComplexAction, AddComplexEffect>(
    AddComplexScreenState()
) {
    override fun onAction(action: AddComplexAction) {
        when (action) {
            AddComplexAction.AddComplex -> addComplex()
            is AddComplexAction.ChangeBallisticCoefficient -> changeBallisticCoefficient(action.ballisticCoefficient)
            is AddComplexAction.ChangeClickPrice -> changeClickPrice(action.clickPrice)
            is AddComplexAction.ChangeGunId -> changeGunId(action.gunId)
            is AddComplexAction.ChangeMuzzleVelocity -> changeMuzzleVelocity(action.muzzleVelocity)
            is AddComplexAction.ChangeName -> changeName(action.name)
            is AddComplexAction.ChangeSightHeight -> changeSightHeight(action.sightHeight)
            is AddComplexAction.ChangeZeroRange -> changeZeroRange(action.zeroRange)
        }
    }

    init {
        viewModelScope.launch {
            analyticsRepository.sendEvent(FirebaseEventsEnum.ADD_COMPLEX.key)
        }
    }

    private fun addComplex() {
        if (
            uiState.value.name == "" // TODO add check
        ) {
            sendEffect(AddComplexEffect.ShowError(resourceProvider.getString(CoreStringRes.all_fields_must_be_filled)))
        } else {
            viewModelScope.launch {
                userRepository.addComplex(
                    name = uiState.value.name,
                    gunId = uiState.value.gunId,
                    zeroRange = uiState.value.zeroRange,
                    sightHeight = (uiState.value.sightHeight.toFloatOrNull() ?: 0f)/100,
                    clickPrice = uiState.value.clickPrice.key,
                    muzzleVelocity = uiState.value.muzzleVelocity,
                    ballisticCoefficient = uiState.value.ballisticCoefficient.toFloatOrNull() ?: 0f
                ) //TODO add Network Response
                sendEffect(AddComplexEffect.Back)
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

    private fun changeGunId(gunId: String) {
        viewModelScope.launch {
            changeState {
                it.copy(gunId = gunId)
            }
        }
    }

    private fun changeZeroRange(zeroRange: String) {
        viewModelScope.launch {
            changeState {
                it.copy(zeroRange = zeroRange.toIntOrNull() ?: 0)
            }
        }
    }

    private fun changeSightHeight(sightHeight: String) {
        viewModelScope.launch {
            changeState {
                it.copy(sightHeight = sightHeight.replace(',', '.'))
            }
        }
    }

    private fun changeClickPrice(clickPrice: String) {
        viewModelScope.launch {
            changeState { state ->
                state.copy(clickPrice = ClickPriceEnum.entries.first { it.key == clickPrice })
            }
        }
    }

    private fun changeMuzzleVelocity(muzzleVelocity: String) {
        viewModelScope.launch {
            changeState {
                it.copy(muzzleVelocity = muzzleVelocity.toIntOrNull() ?: 0)
            }
        }
    }

    private fun changeBallisticCoefficient(ballisticCoefficient: String) {
        viewModelScope.launch {
            changeState {
                it.copy(ballisticCoefficient = ballisticCoefficient.replace(',', '.'))
            }
        }
    }


}