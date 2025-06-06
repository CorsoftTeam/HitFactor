package com.corsoft.services.internal.model

import androidx.compose.runtime.Immutable

@Immutable
data class UserModel(
    val name: String = "",
    val lastName: String = "",
    val login: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val uuid: String = "",
)