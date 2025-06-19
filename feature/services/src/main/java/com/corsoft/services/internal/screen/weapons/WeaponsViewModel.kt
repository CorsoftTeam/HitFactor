package com.corsoft.services.internal.screen.weapons

import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class WeaponsViewModel(
    private val userRepository: UserRepository
) : MviViewModel<WeaponsScreenState, WeaponsAction, WeaponsEffect>(
    WeaponsScreenState()
) {
    override fun onAction(action: WeaponsAction) {
        when(action) {
            WeaponsAction.Refresh -> loadData()
        }
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            userRepository.getMyGuns().doOn(
                success = {
                    changeState { state ->
                        state.copy(weaponsList = it.map { it.toUiModel() })
                    }
                },
                failed = {
                    changeState { state ->
                        state.copy(weaponsList = listOf())
                    }
                }
            )
        }
    }
}