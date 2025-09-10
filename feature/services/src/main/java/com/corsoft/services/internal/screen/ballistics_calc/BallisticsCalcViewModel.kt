package com.corsoft.services.internal.screen.ballistics_calc

import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.ballistic.api.BallisticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class BallisticsCalcViewModel(
    private val ballisticsRepository: BallisticsRepository,
    private val userRepository: UserRepository
) : MviViewModel<BallisticsCalcScreenState, BallisticsCalcAction, BallisticsCalcEffect>(
    BallisticsCalcScreenState()
) {

    private var calculatingJob: Job = Job()

    override fun onAction(action: BallisticsCalcAction) {
        when (action) {
            BallisticsCalcAction.Calculate -> calculate()
            is BallisticsCalcAction.ChangeRange -> changeRange(action.range)
            is BallisticsCalcAction.ChangeZeroRange -> changeZeroRange(action.range)
            is BallisticsCalcAction.ChangeWindAngle -> changeWindAngle(action.angle)
            is BallisticsCalcAction.ChangeWindSpeed -> changeWindSpeed(action.speed)
            is BallisticsCalcAction.ChangeComplex -> newComplex(action.name)
            BallisticsCalcAction.Refresh -> refreshList()
            BallisticsCalcAction.CompleteChangeRange -> generateCharts()
        }
    }

    private fun refreshList() {
        viewModelScope.launch {
            val complexes = userRepository.getComplexes()
            changeState {
                it.copy(
                    complexes = complexes
                )
            }
        }
    }

    private fun newComplex(name: String) {
        viewModelScope.launch {
            val complex = uiState.value.complexes.first { it.name == name }
            changeState {
                it.copy(
                    selectedComplex = complex,
                    zeroRangeM = complex.zeroRange.toDouble()
                )
            }
            generateCharts()
            calculate()
        }
    }

    private fun generateCharts() {
        uiState.value.selectedComplex?.let {
            val dropData = mutableListOf<Double>()
            val speedData = mutableListOf<Double>()
            val timeData = mutableListOf<Double>()
            for (i in 0..1000 step 20) {
                val solve = ballisticsRepository.solve(
                    ballisticCoefficient = it.ballisticCoefficient.toDouble(),
                    muzzleVelocityMs = it.muzzleVelocity.toDouble(),
                    zeroRangeM = uiState.value.zeroRangeM,
                    rangeM = i.toDouble(),
                    sightHeightM = it.sightHeight.toDouble(),
                    shootingAngleDeg = uiState.value.shootingAngleDeg,
                    windSpeedMs = uiState.value.windSpeedMs,
                    windAngleDeg = uiState.value.windAngleDeg,
                )
                dropData.add(solve.pathM)
                speedData.add(solve.speed)
                timeData.add(solve.time)
            }
            changeState { state ->
                state.copy(
                    dropChartData = dropData.toList(),
                    speedChartData = speedData.toList(),
                    timeChartData = timeData.toList()
                )
            }
        }
    }

    private fun calculate() {
        calculatingJob.cancel()
        calculatingJob = viewModelScope.launch {
            uiState.value.selectedComplex?.let { complex ->
                val result = ballisticsRepository.solve(
                    ballisticCoefficient = complex.ballisticCoefficient.toDouble(),
                    muzzleVelocityMs = complex.muzzleVelocity.toDouble(),
                    zeroRangeM = uiState.value.zeroRangeM,
                    rangeM = uiState.value.rangeM,
                    sightHeightM = complex.sightHeight.toDouble(),
                    shootingAngleDeg = uiState.value.shootingAngleDeg,
                    windSpeedMs = uiState.value.windSpeedMs,
                    windAngleDeg = uiState.value.windAngleDeg,
                )
                changeState {
                    it.copy(
                        verticalDrop = result.pathM,
                        horizontalDrop = result.windage
                    )
                }
            }
        }
    }

    private fun changeRange(range: Double) {
        changeState {
            it.copy(rangeM = range)
        }
        calculate()
    }

    private fun changeZeroRange(range: Double) {
        changeState {
            it.copy(zeroRangeM = range)
        }
        calculate()
    }

    private fun changeWindSpeed(speed: Double) {
        changeState {
            it.copy(windSpeedMs = speed)
        }
        calculate()
    }

    private fun changeWindAngle(angle: Double) {
        changeState {
            it.copy(windAngleDeg = angle)
        }
        calculate()
    }


}