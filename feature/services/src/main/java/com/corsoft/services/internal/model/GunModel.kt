package com.corsoft.services.internal.model

import com.corsoft.services.internal.component.enum.GunTypeEnum

internal data class GunModel(
    val name: String,
    val caliber: String,
    val serialNumber: String,
    val gunType: GunTypeEnum,
)