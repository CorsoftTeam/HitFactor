package com.corsoft.services.internal.model

import com.corsoft.services.internal.component.enum.GunTypeEnum

data class GunModel(
    val id: String = "",
    val name: String = "",
    val caliber: String = "",
    val serialNumber: String = "",
    val gunType: GunTypeEnum = GunTypeEnum.PISTOL,
    val shotCount: Int = 0,
    val shotCountBeforeClean: Int = 0
)