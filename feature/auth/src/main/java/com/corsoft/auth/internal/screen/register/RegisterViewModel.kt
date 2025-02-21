package com.corsoft.auth.internal.screen.register

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.network.model.NetworkResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlin.time.Duration

internal class RegisterViewModel(
    private val authRepository: AuthRepository,
) : MviViewModel<RegisterScreenState, RegisterAction, RegisterEffect>(
    RegisterScreenState()
) {

    init {
        setLoading(false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun register() {
        viewModelScope.launch {
            setLoading(true)
            val response =
                authRepository.register(
                    login = uiState.value.login,
                    password = uiState.value.password,
                    email = uiState.value.email,
                    name = uiState.value.name
                )
            when (response) {
                is NetworkResponse.Success -> {
                    viewModelScope.launch {
                        sendEffect(RegisterEffect.ShowError("Регистрация успешна!"))
                        delay(java.time.Duration.ofSeconds(2))
                        sendEffect(RegisterEffect.Register)
                    }
                }

                is NetworkResponse.Failed -> {
                    setLoading(false)
                    sendEffect(RegisterEffect.ShowError(response.getErrorMessage()))
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

    private fun setLoading(state: Boolean){
        changeState {
            it.copy(
                isLoading = state
            )
        }
    }

}