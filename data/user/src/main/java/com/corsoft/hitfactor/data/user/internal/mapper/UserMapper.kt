package com.corsoft.hitfactor.data.user.internal.mapper

import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.hitfactor.data.user.internal.network.model.response.GunItemResponse
import com.corsoft.hitfactor.data.user.internal.network.model.response.UserResponse

internal fun UserResponse.toModel(): User =
    User(
        name = name,
        lastName = lastName,
        login = login,
        email = email,
        phoneNumber = phoneNumber,
        uuid = uuid
    )

internal fun GunItemResponse.toModel(): Gun =
    Gun(
        name = name,
        caliber = caliber,
        serialNumber = serialNumber,
        gunType = gunType
    )