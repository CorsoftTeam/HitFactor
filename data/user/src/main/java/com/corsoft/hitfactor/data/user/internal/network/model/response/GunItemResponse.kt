package com.corsoft.hitfactor.data.user.internal.network.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class GunItemResponse(
    @SerializedName("id")
    val token: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("gun_type")
    val gunType: String,
    @SerializedName("caliber")
    val caliber: String,
    @SerializedName("serial_number")
    val serialNumber: String,
)