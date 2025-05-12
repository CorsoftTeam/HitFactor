package com.corsoft.hitfactor.data.user.internal.network.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class AddGunRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("gun_type")
    val gunType: String,
    @SerializedName("caliber")
    val caliber: String,
    @SerializedName("serial_number")
    val serialNumber: String,
)