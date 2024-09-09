package com.corsoft.network.model

import kotlinx.serialization.Serializable

@Serializable
internal class ServerError(
    val message: String,
    val statusCode: Int
)
