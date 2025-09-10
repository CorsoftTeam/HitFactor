package com.corsoft.hitfactor.data.user.api.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "complexes")
data class ComplexEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    // gun
    val gunId: String = "",
    val zeroRange: Int = 0,
    // sight
    val sightHeight: Float = 0f,
    val clickPrice: String = "",
    // shell
    val muzzleVelocity: Int = 0,
    val ballisticCoefficient: Float = 0f
)