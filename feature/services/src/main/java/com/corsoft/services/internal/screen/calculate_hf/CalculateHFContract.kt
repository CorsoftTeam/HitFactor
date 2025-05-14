package com.corsoft.services.internal.screen.calculate_hf

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
data class CalculateHFScreenState(
    val time: Int = 0,
    val isMajor: Boolean = false,
    val alphaCount: Int = 0,
    val charlieCount: Int = 0,
    val deltaCount: Int = 0,
    val missCount: Int = 0,
    val noShootCount: Int = 0,
    val procedureCount: Int = 0,
    val totalShootCount: Int = 0,
    val isLoading: Boolean = true
) : MviState {
    private val points: Int
        get() = if (isMajor) {
            alphaCount * 5 + charlieCount * 4 + deltaCount * 2 - (missCount + noShootCount + procedureCount) * 10
        } else {
            alphaCount * 5 + charlieCount * 3 + deltaCount * 1 - (missCount + noShootCount + procedureCount) * 10
        }
    val hitFactor: Double
        get() = if (time != 0) points / (time / 1000.0) else 0.0
}

internal sealed interface CalculateHFAction : MviAction {

    data object AddAlpha : CalculateHFAction

    data object AddCharlie : CalculateHFAction

    data object AddDelta : CalculateHFAction

    data object AddMiss : CalculateHFAction

    data object AddNoShoot : CalculateHFAction

    data object AddProcedure : CalculateHFAction

    data object Save : CalculateHFAction

    data object Reset : CalculateHFAction

}

internal sealed interface CalculateHFEffect : MviEffect