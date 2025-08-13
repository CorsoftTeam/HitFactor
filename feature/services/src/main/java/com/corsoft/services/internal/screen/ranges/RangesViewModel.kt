package com.corsoft.services.internal.screen.ranges

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class RangesViewModel(
    private val userRepository: UserRepository,
    analytics: AnalyticsRepository
) : MviViewModel<RangesScreenState, RangesAction, RangesEffect>(
    RangesScreenState()
) {
    override fun onAction(action: RangesAction) {
        when(action) {
            is RangesAction.Refresh -> loadData()
            is RangesAction.ChangeCity -> changeCity(action.city)
        }
    }

    init {
        loadData()
        analytics.sendEvent(FirebaseEventsEnum.OPEN_RANGES.key)
    }

    private fun loadData() {
        viewModelScope.launch {
            userRepository.getCities().doOn(
                success = { response ->
                    changeState {
                        it.copy(
                            citiesList = response.map { city -> city.toUiModel() }
                        )
                    }
                },
                failed = {

                }
            )
        }
    }

    private fun changeCity(city: String) {
        changeState { state ->
            state.copy(
                currentCity = uiState.value.citiesList.first { it.name == city }
            )
        }
        loadRanges()
    }

    private fun loadRanges() {
        viewModelScope.launch {
            userRepository.getRanges(uiState.value.currentCity.id).doOn(
                success = { response ->
                    changeState { state ->
                        state.copy(
                            rangeList = response.map { it.toUiModel() }
                        )
                    }
                },
                failed = {

                }
            )
        }
    }
}