package com.corsoft.auth.internal.screen.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.auth.internal.screen.register.RegisterEffect
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.network.model.NetworkResponse
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authRepository: AuthRepository
) : MviViewModel<LoginScreenModel, LoginAction, LoginEffect>(
    LoginScreenModel()
) {
    private fun login() {
        viewModelScope.launch {
            val response =
                authRepository.login(
                    login = uiState.value.login,
                    password = uiState.value.password
                )
            when (response) {
                is NetworkResponse.Success -> {
                    sendEffect(LoginEffect.Login)
                }

                is NetworkResponse.Failed -> {
                    sendEffect(LoginEffect.ShowError(response.getErrorMessage()))
                }
            }
        }
    }

    override fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> {
                login()
            }

            is LoginAction.UpdateLogin -> {
                changeState { it.copy(login = action.login) }
            }

            is LoginAction.UpdatePassword -> {
                changeState { it.copy(password = action.password) }
            }
        }
    }
}