package com.corsoft.services.internal.screen.results

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import kotlinx.coroutines.launch

internal class ResultsViewModel(
    val userRepository: UserRepository,
    analyticsRepository: AnalyticsRepository
): MviViewModel<ResultsScreenState, ResultsAction, ResultsEffect>(
    ResultsScreenState()
) {
    override fun onAction(action: ResultsAction) {
        when(action){
            is ResultsAction.OnDelete -> delete(action.id)
        }
    }

    init {
        viewModelScope.launch {
            analyticsRepository.sendEvent(FirebaseEventsEnum.OPEN_RESULTS.key)
            update()
        }
    }

    private fun update() {
        viewModelScope.launch {
            val results = userRepository.getResults()
            changeState {
                it.copy(
                    results = results
                )
            }
        }
    }

    private fun delete(id: Long) {
        viewModelScope.launch {
            userRepository.deleteResultById(id)
            update()
        }
    }

}