package com.corsoft.hitfactor.data.user.api.model

import java.time.LocalDateTime

data class Training(
    val id: String = "",
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val length: Int = 0,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String = "",
    val shotCount: Int = 0
)
