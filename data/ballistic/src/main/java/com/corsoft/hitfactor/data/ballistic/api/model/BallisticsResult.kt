package com.corsoft.hitfactor.data.ballistic.api.model

data class BallisticsResult(
    val rangeM: Double,
    val pathM: Double,
    val windage: Double,
    val speed: Double,
    val time: Double
)