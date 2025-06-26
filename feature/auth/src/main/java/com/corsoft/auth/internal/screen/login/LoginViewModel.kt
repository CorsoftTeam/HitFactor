package com.corsoft.auth.internal.screen.login

import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.network.model.NetworkResponse
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authRepository: AuthRepository,
    private val paymentsRepository: PaymentsRepository
) : MviViewModel<LoginScreenModel, LoginAction, LoginEffect>(
    LoginScreenModel()
) {

    init {
        checkUser()
    }

    private fun login() {
        viewModelScope.launch {
            setLoading(true)
            val response =
                authRepository.login(
                    email = uiState.value.email,
                    password = uiState.value.password
                )
            when (response) {
                is NetworkResponse.Success -> {
                    checkUser()
                }

                is NetworkResponse.Failed -> {
                    setLoading(false)
                    sendEffect(LoginEffect.ShowError(response.getErrorMessage()))
                }
            }
        }
    }

    private fun checkUser() {
        viewModelScope.launch {
            setLoading(true)
            if (authRepository.isUserAuthorised()) {
                if (authRepository.isUserVip()) {
                    sendEffect(LoginEffect.GoToServices)
                } else {
                    paymentsRepository.isAuth { isAuth ->
                        viewModelScope.launch {
                            if (isAuth) {
                                paymentsRepository.isSub { isSub ->
                                    if (isSub == true) {
                                        sendEffect(LoginEffect.GoToServices)
                                    } else {
                                        sendEffect(LoginEffect.GoToPayment)
                                    }
                                }
                            } else {
                                sendEffect(LoginEffect.GoToPayment)
                            }
                        }
                    }
                }
            }
            setLoading(false)
        }
    }

    override fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> {
                login()
            }

            is LoginAction.UpdateLogin -> {
                changeState { it.copy(email = action.login) }
            }

            is LoginAction.UpdatePassword -> {
                changeState { it.copy(password = action.password) }
            }
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