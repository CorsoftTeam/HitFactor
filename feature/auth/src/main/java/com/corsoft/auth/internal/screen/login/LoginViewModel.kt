package com.corsoft.auth.internal.screen.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.ResourceProvider
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.payments.api.PaymentsRepository
import com.corsoft.network.model.NetworkResponse
import com.corsoft.resources.CoreStringRes
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class LoginViewModel(
    private val authRepository: AuthRepository,
    private val paymentsRepository: PaymentsRepository,
    private val resourceProvider: ResourceProvider
) : MviViewModel<LoginScreenModel, LoginAction, LoginEffect>(
    LoginScreenModel()
) {

    init {
        checkUser()
    }

    private fun login() {
        if (
            uiState.value.email.isEmpty() ||
            uiState.value.password.isEmpty()
        ) {
            sendEffect(LoginEffect.ShowError(resourceProvider.getString(CoreStringRes.all_fields_must_be_filled)))
        } else {
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
    }

    private fun checkUser() {
        viewModelScope.launch {
            setLoading(true)
            if (authRepository.isUserAuthorised()) {
                if (authRepository.isUserVip()) {
                    sendEffect(LoginEffect.GoToServices)
                } else {
                    if (paymentsRepository.isAuth() == true) {
                        if (paymentsRepository.isSub() == true) {
                            sendEffect(LoginEffect.GoToServices)
                        } else {
                            sendEffect(LoginEffect.GoToPayment)
                        }
                    } else {
                        sendEffect(LoginEffect.GoToPayment)
                    }
                }
            }
            setLoading(false)
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            setLoading(true)
            if (uiState.value.email.isNotEmpty()) {
                authRepository.sendResetPasswordLink(uiState.value.email).doOn(
                    success = {
                        setLoading(false)
                        sendEffect(
                            LoginEffect.ShowError(
                                resourceProvider.getString(
                                    CoreStringRes.email_recover_link_sended
                                )
                            )
                        )
                    },
                    failed = { response ->
                        setLoading(false)
                        sendEffect(LoginEffect.ShowError(response.getErrorMessage()))
                    }
                )
            } else {
                sendEffect(LoginEffect.ShowError(resourceProvider.getString(CoreStringRes.email_must_be_filled)))
                setLoading(false)
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

            is LoginAction.ResetPassword -> {
                resetPassword()
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