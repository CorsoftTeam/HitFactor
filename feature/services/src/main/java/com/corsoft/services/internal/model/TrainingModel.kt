package com.corsoft.services.internal.model

import java.time.LocalDateTime

data class TrainingModel(
    val id: String = "",
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val length: Int = 0,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String = "",
    val shotCount: Int = 0
)