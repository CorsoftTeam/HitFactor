package com.corsoft.auth.internal.screen.register

import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import kotlinx.coroutines.launch

internal class RegisterViewModel(
    private val authRepository: AuthRepository
) : MviViewModel<RegisterScreenState, RegisterAction, RegisterEffect>(
    RegisterScreenState()
) {
    fun register() {
        viewModelScope.launch {
            //TODO: add register
            sendEffect(RegisterEffect.Register)
        }
    }

    override fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.Register -> {
                sendEffect(RegisterEffect.ShowError("Бекендер кушает, подождите"))
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
        }
    }

}