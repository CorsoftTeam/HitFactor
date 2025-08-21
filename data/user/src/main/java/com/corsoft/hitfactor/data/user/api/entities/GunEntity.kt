package com.corsoft.hitfactor.data.user.api.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guns")
data class GunEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val caliber: String = "",
    val serialNumber: String = "",
    val gunType: String = "",
    val shotCount: Int = 0,
    val shotsBeforeClean: Int = 0
)