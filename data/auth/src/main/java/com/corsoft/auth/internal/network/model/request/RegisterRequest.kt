package com.corsoft.auth.internal.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequest(
    @SerialName("login")
    val login: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("password")
    val password: String?,
)