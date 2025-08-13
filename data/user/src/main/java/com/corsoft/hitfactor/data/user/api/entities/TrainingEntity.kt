package com.corsoft.hitfactor.data.user.api.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey val id: String,
    val dateTime: String = "",
    val length: Int = 0,
    val hfScore: Float = 0f,
    val note: String = "",
    val weaponId: String = "",
    val shotCount: Int = 0
)