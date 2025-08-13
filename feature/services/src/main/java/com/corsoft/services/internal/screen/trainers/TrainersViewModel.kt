package com.corsoft.services.internal.screen.trainers

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class TrainersViewModel(
    private val userRepository: UserRepository,
    analytics: AnalyticsRepository
) : MviViewModel<TrainersScreenState, TrainersAction, TrainersEffect>(
    TrainersScreenState()
) {
    override fun onAction(action: TrainersAction) {
        when(action) {
            is TrainersAction.Refresh -> loadData()
            is TrainersAction.ChangeCity -> changeCity(action.city)
        }
    }

    init {
        loadData()
        analytics.sendEvent(FirebaseEventsEnum.OPEN_TRAINERS.key)
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
        loadTrainers()
    }

    private fun loadTrainers() {
        viewModelScope.launch {
            userRepository.getTrainers(uiState.value.currentCity.id).doOn(
                success = { response ->
                    changeState { state ->
                        state.copy(
                            trainersList = response.map { it.toUiModel() }
                        )
                    }
                },
                failed = {

                }
            )
        }
    }
}