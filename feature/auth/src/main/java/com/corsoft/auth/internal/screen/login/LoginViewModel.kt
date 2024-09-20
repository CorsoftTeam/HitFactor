package com.corsoft.auth.internal.screen.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val authRepository: AuthRepository
) : MviViewModel<LoginScreenModel, LoginAction, LoginEffect>(
    LoginScreenModel()
) {
    fun login() {
        viewModelScope.launch {
            //TODO: add login
            Log.d("r", authRepository.login("user1", "password1").toString())
            sendEffect(LoginEffect.Login)
        }
    }

    override fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.Login -> {
                sendEffect(LoginEffect.Login)
                //sendEffect(LoginEffect.ShowError("Бекендер кушает, подождите"))
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