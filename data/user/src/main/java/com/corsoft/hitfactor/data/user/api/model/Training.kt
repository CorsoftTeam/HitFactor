package com.corsoft.hitfactor.data.user.api.model

import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(26)
data class Training(
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val length: Int = 0,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String = "",
    val shotCount: Int = 0
)
