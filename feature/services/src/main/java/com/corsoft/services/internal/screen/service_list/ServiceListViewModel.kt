package com.corsoft.services.internal.screen.service_list

import androidx.lifecycle.viewModelScope
import com.corsoft.common.FirebaseEventsEnum
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.component.enum.ServicesGroupsEnum
import kotlinx.coroutines.launch

internal class ServiceListViewModel(
    private val userRepository: UserRepository,
    analyticsRepository: AnalyticsRepository
) : MviViewModel<ServiceListScreenState, ServiceListAction, ServiceListEffect>(
    ServiceListScreenState()
) {
    override fun onAction(action: ServiceListAction) {
        when(action) {
            is ServiceListAction.Refresh -> update()
            is ServiceListAction.ChangeMode -> changeMode()
        }
    }

    init {
        analyticsRepository.sendEvent(FirebaseEventsEnum.OPEN_SERVICES.key)
        update()
    }

    private fun update() {
        viewModelScope.launch {
            val serviceGroup = userRepository.getServiceGroup()
            val serviceMode = userRepository.getServiceMode()
            changeState { state ->
                state.copy(
                    serviceGroup = ServicesGroupsEnum.entries.firstOrNull { it.groupName == serviceGroup }
                        ?: ServicesGroupsEnum.ALL,
                    isServiceModeGrid = serviceMode
                )
            }
        }
    }

    private fun changeMode() {
        viewModelScope.launch {
            userRepository.setServiceMode(!uiState.value.isServiceModeGrid)
            update()
        }
    }

}