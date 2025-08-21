package com.corsoft.services.internal.screen.trainings

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class TrainingsViewModel(
    private val userRepository: UserRepository,
    analytics: AnalyticsRepository
): MviViewModel<TrainingsScreenState, TrainingsAction, TrainingsEffect>(
    TrainingsScreenState()
) {
    override fun onAction(action: TrainingsAction) {
        when (action) {
            TrainingsAction.Refresh -> loadData()
        }
    }

    init {
        loadData()
        analytics.sendEvent(FirebaseEventsEnum.OPEN_CALENDAR.key)
    }

    private fun loadData() {
        viewModelScope.launch {
            setLoading(true)
            userRepository.getTrainings().doOn(
                success = {
                    changeState { state ->
                        state.copy(trainings = it.map { it.toUiModel() })
                    }
                },
                failed = {
                    //TODO: handle errors
                }
            )
            setLoading(false)
        }
    }

    private fun setLoading(state: Boolean){
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}