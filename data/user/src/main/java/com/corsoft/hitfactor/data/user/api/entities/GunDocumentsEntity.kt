package com.corsoft.hitfactor.data.user.api.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gun_documents")
data class GunDocumentsEntity(
    @PrimaryKey val gunId: String,
    val docFirstPageUri: String = "",
    val docSecondPageUri: String = "",
    val licenseFirstPageUri: String = "",
    val licenseSecondPageUri: String = ""
)