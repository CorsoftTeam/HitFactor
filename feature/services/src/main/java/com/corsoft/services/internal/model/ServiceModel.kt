package com.corsoft.services.internal.model

import com.ramcosta.composedestinations.spec.Direction

internal data class ServiceModel(
    val name: String,
    val iconRes: Int,
    val destination: Direction
)
