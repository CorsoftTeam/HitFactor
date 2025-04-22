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
    val totalShootCount: Int = 0,
    val isLoading: Boolean = true
) : MviState {
    val points: Int
        get() = if (isMajor) {
            alphaCount * 5 + charlieCount * 4 + deltaCount * 2
        } else {
            alphaCount * 5 + charlieCount * 3 + deltaCount * 1
        }
    val hitFactor: Double
        get() = points / (time / 1000.0)
}

internal sealed interface CalculateHFAction : MviAction

internal sealed interface CalculateHFEffect : MviEffect