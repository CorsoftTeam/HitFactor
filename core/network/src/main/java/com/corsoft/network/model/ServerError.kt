package com.corsoft.network.model

import kotlinx.serialization.Serializable

@Serializable
internal class ServerError(
    val error: String
)
