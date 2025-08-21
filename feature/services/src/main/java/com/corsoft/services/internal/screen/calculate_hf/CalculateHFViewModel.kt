package com.corsoft.services.internal.screen.calculate_hf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.api.entities.ResultEntity
import com.ramcosta.composedestinations.generated.services.destinations.CalculateHFScreenDestination
import kotlinx.coroutines.launch

internal class CalculateHFViewModel(
    savedStateHandle: SavedStateHandle,
    analytics: AnalyticsRepository,
    val userRepository: UserRepository
) :
    MviViewModel<CalculateHFScreenState, CalculateHFAction, CalculateHFEffect>(
        CalculateHFScreenState()
    ) {

    private val navArgs = CalculateHFScreenDestination.argsFrom(savedStateHandle)

    init {
        changeState {
            it.copy(
                time = navArgs.time
            )
        }
        analytics.sendEvent(FirebaseEventsEnum.OPEN_CALC.key)
    }

    override fun onAction(action: CalculateHFAction) {
        when (action) {
            CalculateHFAction.AddAlpha -> addAlpha()
            CalculateHFAction.AddCharlie -> addCharlie()
            CalculateHFAction.AddDelta -> addDelta()
            CalculateHFAction.AddMiss -> addMiss()
            CalculateHFAction.AddNoShoot -> addNoShoot()
            CalculateHFAction.AddProcedure -> addProcedure()
            CalculateHFAction.Reset -> reset()
            is CalculateHFAction.Save -> save(action.name)
        }
    }

    private fun save(name: String) {
        viewModelScope.launch {
            userRepository.addResult(
                name = name,
                score = uiState.value.points,
                time = uiState.value.time.toLong(),
                hitFactor = uiState.value.hitFactor.toFloat()
            )
        }
    }

    private fun reset() {
        changeState {
            it.copy(
                alphaCount = 0,
                charlieCount = 0,
                deltaCount = 0,
                missCount = 0,
                noShootCount = 0,
                procedureCount = 0
            )
        }
    }

    private fun addAlpha() {
        changeState {
            it.copy(
                alphaCount = it.alphaCount + 1
            )
        }
    }

    private fun addCharlie() {
        changeState {
            it.copy(
                charlieCount = it.charlieCount + 1
            )
        }
    }

    private fun addDelta() {
        changeState {
            it.copy(
                deltaCount = it.deltaCount + 1
            )
        }
    }

    private fun addMiss() {
        changeState {
            it.copy(
                missCount = it.missCount + 1
            )
        }
    }

    private fun addNoShoot() {
        changeState {
            it.copy(
                noShootCount = it.noShootCount + 1
            )
        }
    }

    private fun addProcedure() {
        changeState {
            it.copy(
                procedureCount = it.procedureCount + 1
            )
        }
    }

}