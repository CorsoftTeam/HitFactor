package com.corsoft.auth.internal.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthRequest(
    @SerialName("login")
    val login: String,
    @SerialName("password")
    val password: String,
)