package com.corsoft.hitfactor.data.user.api.model

data class Gun(
    val id: String = "",
    val name: String = "",
    val caliber: String = "",
    val serialNumber: String = "",
    val gunType: String = "",
    val shotCount: Int = 0
)