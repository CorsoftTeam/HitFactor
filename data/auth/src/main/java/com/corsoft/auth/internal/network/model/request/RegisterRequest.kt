package com.corsoft.auth.internal.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RegisterRequest(
    @SerialName("name")
    val name: String,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("login")
    val login: String,
    @SerialName("email")
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String? = null,
    @SerialName("password")
    val password: String,
)