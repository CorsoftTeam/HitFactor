package com.corsoft.auth.internal.screen.register

import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.network.model.NetworkResponse
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val authRepository: AuthRepository
) : MviViewModel<RegisterScreenState, RegisterAction, RegisterEffect>(
    RegisterScreenState()
) {
    private fun register() {
        viewModelScope.launch {
            viewModelScope.launch {
                val response =
                    authRepository.register(
                        login = uiState.value.login,
                        password = uiState.value.password,
                        email = uiState.value.email
                    )
                when (response) {
                    is NetworkResponse.Success -> {
                        sendEffect(RegisterEffect.Register)
                    }

                    is NetworkResponse.Failed -> {
                        sendEffect(RegisterEffect.ShowError(response.getErrorMessage()))
                    }
                }
            }
        }
    }

    override fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.Register -> {
                register()
            }

            is RegisterAction.UpdateLogin -> {
                changeState { it.copy(login = action.login) }
            }

            is RegisterAction.UpdatePassword -> {
                changeState { it.copy(password = action.password) }
            }

            is RegisterAction.UpdateName -> {
                changeState { it.copy(name = action.name) }
            }

            is RegisterAction.UpdatePhone -> {
                changeState { it.copy(phone = action.phone) }
            }

            is RegisterAction.UpdateEmail -> {
                changeState { it.copy(email = action.email) }
            }
        }
    }

}