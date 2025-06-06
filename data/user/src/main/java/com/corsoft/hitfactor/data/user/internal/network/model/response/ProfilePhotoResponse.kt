package com.corsoft.hitfactor.data.user.internal.network.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfilePhotoResponse(
    @SerializedName("user_image")
    val profilePhotoUrl: String,
)