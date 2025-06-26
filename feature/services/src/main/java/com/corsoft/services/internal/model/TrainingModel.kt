package com.corsoft.services.internal.model

import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(26)
data class TrainingModel(
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val length: Int = 0,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String = "",
    val shotCount: Int = 0
)