package com.corsoft.auth.internal.network.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class LoginRequest(
    val login: String,
    val password: String
)
