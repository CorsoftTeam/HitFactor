package com.corsoft.services.internal.model

import com.corsoft.services.internal.component.enum.GunTypeEnum

data class GunModel(
    val id: Int = 0,
    val name: String = "",
    val caliber: String = "",
    val serialNumber: String = "",
    val gunType: GunTypeEnum = GunTypeEnum.PISTOL,
    val shotCount: Int = 0,
)