package com.corsoft.services.internal.screen.ballistics_calc

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState
import com.corsoft.hitfactor.data.user.api.entities.ComplexEntity
import com.corsoft.services.internal.component.enum.ClickMeasureEnum
import com.corsoft.services.internal.component.enum.ClickPriceEnum
import kotlin.math.roundToInt

@Immutable
internal data class BallisticsCalcScreenState(
    val complexes: List<ComplexEntity> = emptyList(),
    val selectedComplex: ComplexEntity? = null,
    val rangeM: Double = 100.0,
    val windSpeedMs: Double = 0.0,
    val windAngleDeg: Double = 0.0,

    val ballisticCoefficient: Double = 0.445,
    val muzzleVelocityMs: Double = 790.0,
    val sightHeightM: Double = 0.05,
    val shootingAngleDeg: Double = 0.0,
    val zeroRangeM: Double = 200.0,


    val clickMeasure: ClickMeasureEnum = ClickMeasureEnum.MOA,
    //result
    val verticalDrop: Double = 0.0,
    val horizontalDrop: Double = 0.0,
    val dropChartData: List<Double> = emptyList(),
    val speedChartData: List<Double> = emptyList(),
    val timeChartData: List<Double> = emptyList()
) : MviState {
    val clickPrice: ClickPriceEnum
        get() = ClickPriceEnum.entries.firstOrNull { it.key == selectedComplex?.clickPrice }
            ?: ClickPriceEnum.MOA12
    val verticalMOA: Double
        get() = if (rangeM == 0.0) 0.0 else -(verticalDrop / rangeM).times(34.38)
    val horizontalMOA: Double
        get() = if (rangeM == 0.0) 0.0 else -(horizontalDrop / rangeM).times(34.38)
    val verticalMIL: Double
        get() = if (rangeM == 0.0) 0.0 else -(verticalDrop / rangeM).times(10)
    val horizontalMIL: Double
        get() = if (rangeM == 0.0) 0.0 else -(horizontalDrop / rangeM).times(10)
    val verticalMOAString: String
        get() = if (verticalMOA > 0) "+"+String.format("%.2f", verticalMOA) else String.format("%.2f", verticalMOA)
    val horizontalMOAString: String
        get() = if (horizontalMOA > 0) "+"+String.format("%.2f", horizontalMOA) else String.format("%.2f", horizontalMOA)
    val verticalMILString: String
        get() = if (verticalMIL > 0) "+"+String.format("%.2f", verticalMIL) else String.format("%.2f", verticalMIL)
    val horizontalMILString: String
        get() = if (horizontalMIL > 0) "+"+String.format("%.2f", horizontalMIL) else String.format("%.2f", horizontalMIL)
    val verticalClicks: Int
        get() {
            return if (rangeM == 0.0) {
                0
            } else {
                when (clickPrice) {
                    ClickPriceEnum.MOA18 -> (verticalMOA*8).roundToInt()
                    ClickPriceEnum.MOA14 -> (verticalMOA*4).roundToInt()
                    ClickPriceEnum.MOA12 -> (verticalMOA*2).roundToInt()
                    ClickPriceEnum.MIL01 -> (verticalMIL*10).roundToInt()
                }
            }
        }
    val horizontalClicks: Int
        get() {
            return if (rangeM == 0.0) {
                0
            } else {
                when (clickPrice) {
                    ClickPriceEnum.MOA18 -> (horizontalMOA*8).roundToInt()
                    ClickPriceEnum.MOA14 -> (horizontalMOA*4).roundToInt()
                    ClickPriceEnum.MOA12 -> (horizontalMOA*2).roundToInt()
                    ClickPriceEnum.MIL01 -> (horizontalMIL*10).roundToInt()
                }
            }
        }
}

internal sealed interface BallisticsCalcAction : MviAction {
    data object Calculate : BallisticsCalcAction
    data object Refresh : BallisticsCalcAction
    data class ChangeComplex(val name: String) : BallisticsCalcAction
    data class ChangeRange(val range: Double) : BallisticsCalcAction
    data object CompleteChangeRange : BallisticsCalcAction
    data class ChangeZeroRange(val range: Double) : BallisticsCalcAction
    data class ChangeWindSpeed(val speed: Double) : BallisticsCalcAction
    data class ChangeWindAngle(val angle: Double) : BallisticsCalcAction
}

internal sealed interface BallisticsCalcEffect : MviEffect