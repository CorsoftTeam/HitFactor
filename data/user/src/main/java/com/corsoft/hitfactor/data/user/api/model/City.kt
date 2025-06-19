package com.corsoft.hitfactor.data.user.api.model

data class City(
    val id: String = "",
    val name: String = "",
    val rangeList: List<Range> = emptyList()
)