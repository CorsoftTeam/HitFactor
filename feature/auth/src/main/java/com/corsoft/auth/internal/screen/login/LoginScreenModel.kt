package com.corsoft.auth.internal.screen.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginScreenModel(
    val login: String = "",
    val password: String = "",
    val error: String = ""
)
