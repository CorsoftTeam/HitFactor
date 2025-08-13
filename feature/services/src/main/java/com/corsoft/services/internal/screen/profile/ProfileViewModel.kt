package com.corsoft.services.internal.screen.profile

import androidx.compose.runtime.saveable.autoSaver
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.resources.CoreStringRes
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class ProfileViewModel(
    userRepository: UserRepository,
    resourceProvider: ResourceProvider,
    private val authRepository: AuthRepository
) : MviViewModel<ProfileScreenState, ProfileAction, ProfileEffect>(
    ProfileScreenState()
) {
    override fun onAction(action: ProfileAction) {
        when(action) {
            ProfileAction.OnExit -> exit()
        }
    }

    init {
        viewModelScope.launch {
            setLoading(true)
            userRepository.getMe().doOn(
                success = { response ->
                    changeState { state ->
                        state.copy(
                            user = response.toUiModel()
                        )
                    }
                    setLoading(false)
                },
                failed = {
                    sendEffect(ProfileEffect.ShowError(resourceProvider.getString(CoreStringRes.something_wrong)))
                    setLoading(false)
                }
            )
        }
    }

    private fun exit() {
        viewModelScope.launch {
            authRepository.logout()
            sendEffect(ProfileEffect.Logout)
        }
    }

    private fun setLoading(state: Boolean) {
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}