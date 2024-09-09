package com.corsoft.auth.internal.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class LoginResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("id")
    val id: String
)