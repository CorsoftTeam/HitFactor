package com.corsoft.services.internal.mapper

import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.GunModel

internal fun Gun.toUiModel(): GunModel =
    GunModel(
        name = name,
        serialNumber = serialNumber,
        caliber = caliber,
        gunType = GunTypeEnum.fromKey(gunType)
    )