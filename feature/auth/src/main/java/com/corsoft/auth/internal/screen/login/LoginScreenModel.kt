package com.corsoft.auth.internal.screen.login

import androidx.compose.runtime.Immutable
import com.corsoft.common.mvvm.ScreenState

@Immutable
data class LoginScreenModel(
    val login: String = "",
    val password: String = "",
    val error: String = ""
) : ScreenState
