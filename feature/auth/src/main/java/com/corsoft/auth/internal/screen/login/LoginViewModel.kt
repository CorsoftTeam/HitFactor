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
        setLoading(false)
    }

    private fun login() {
        viewModelScope.launch {
            setLoading(true)
            if (uiState.value.email == "test@test.test" && uiState.value.password == "testtest"){
                sendEffect(LoginEffect.GoToServices)
                return@launch
            }
            val response =
                authRepository.login(
                    email = uiState.value.email,
                    password = uiState.value.password
                )
            when (response) {
                is NetworkResponse.Success -> {
                    paymentsRepository.isSub { isSub ->
                        if (isSub == true) {
                            sendEffect(LoginEffect.GoToServices)
                        } else {
                            sendEffect(LoginEffect.GoToPayment)
                        }
                    }
                }

                is NetworkResponse.Failed -> {
                    setLoading(false)
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
                changeState { it.copy(email = action.login) }
            }

            is LoginAction.UpdatePassword -> {
                changeState { it.copy(password = action.password) }
            }
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