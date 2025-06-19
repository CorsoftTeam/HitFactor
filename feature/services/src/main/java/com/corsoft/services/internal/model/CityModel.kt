package com.corsoft.services.internal.model

data class CityModel(
    val id: String = "",
    val name: String = "",
    val ranges: List<RangeModel> = emptyList()
)
