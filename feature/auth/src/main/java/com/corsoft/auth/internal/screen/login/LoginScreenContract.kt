package com.corsoft.auth.internal.screen.login

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.MviAction
import com.corsoft.common.mvvm.MviEffect
import com.corsoft.common.mvvm.MviState

@Immutable
data class LoginScreenModel(
    val login: String = "",
    val password: String = "",
    val error: String = ""
) : MviState

sealed interface LoginAction : MviAction

sealed interface LoginEffect : MviEffect
