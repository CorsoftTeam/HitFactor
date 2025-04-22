package com.corsoft.hitfactor.data.user.internal.network.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("uuid")
    val uuid: String,
)