package com.corsoft.hitfactor.data.user.api.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results")
data class ResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val score: Int = 0,
    val time: Long = 0,
    val hitFactor: Float = 0f
)