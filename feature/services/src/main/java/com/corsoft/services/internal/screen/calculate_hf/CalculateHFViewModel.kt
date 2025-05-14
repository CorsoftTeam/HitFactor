package com.corsoft.services.internal.screen.calculate_hf

import androidx.lifecycle.SavedStateHandle
import com.corsoft.common.mvvm.MviViewModel
import com.ramcosta.composedestinations.generated.services.destinations.CalculateHFScreenDestination

internal class CalculateHFViewModel(
    savedStateHandle: SavedStateHandle
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
            CalculateHFAction.Save -> save()
        }
    }

    private fun save() {
        //TODO: add shooter saving
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