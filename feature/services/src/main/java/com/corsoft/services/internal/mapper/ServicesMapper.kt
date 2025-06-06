package com.corsoft.services.internal.mapper

import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.model.UserModel

internal fun Gun.toUiModel(): GunModel =
    GunModel(
        id = id,
        name = name,
        serialNumber = serialNumber,
        caliber = caliber,
        gunType = GunTypeEnum.fromKey(gunType.lowercase()),
        shotCount = shotCount
    )

internal fun User.toUiModel(): UserModel =
    UserModel(
        uuid = uuid,
        name = name,
        lastName = lastName,
        login = login,
        email = email,
        phoneNumber = phoneNumber
    )