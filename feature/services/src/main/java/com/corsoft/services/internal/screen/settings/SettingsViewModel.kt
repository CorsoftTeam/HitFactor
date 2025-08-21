package com.corsoft.services.internal.screen.settings

import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.component.enum.ServicesGroupsEnum
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    val userRepository: UserRepository
) : MviViewModel<SettingsScreenState, SettingsAction, SettingsEffect>(
    SettingsScreenState()
) {
    override fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnServiceGroupChange -> onServiceGroupChange(action.name)
        }
    }

    init {
        update()
    }

    private fun update() {
        viewModelScope.launch {
            val serviceGroup = userRepository.getServiceGroup()
            changeState { state ->
                state.copy(
                    serviceGroup = ServicesGroupsEnum.entries.firstOrNull { it.groupName == serviceGroup } ?: ServicesGroupsEnum.ALL
                )
            }
        }
    }

    private fun onServiceGroupChange(name: String) {
        viewModelScope.launch {
            userRepository.setServiceGroup(name)
            update()
        }
    }

}