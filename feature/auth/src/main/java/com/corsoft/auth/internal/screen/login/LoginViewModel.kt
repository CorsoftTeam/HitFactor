package com.corsoft.auth.internal.screen.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.corsoft.auth.api.AuthRepository
import com.corsoft.common.mvvm.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginScreenModel>(
    LoginScreenModel()
) {
    fun login() {
        viewModelScope.launch {
            Log.d("r", authRepository.login("user1", "password1").toString())
        }
    }
}