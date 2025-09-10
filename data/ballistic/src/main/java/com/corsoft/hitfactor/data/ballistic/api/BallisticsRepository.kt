package com.corsoft.hitfactor.data.ballistic.api

import com.corsoft.hitfactor.data.ballistic.api.model.BallisticsResult

interface BallisticsRepository {
    fun solve(
        ballisticCoefficient: Double,
        muzzleVelocityMs: Double,
        zeroRangeM: Double,
        rangeM: Double,
        sightHeightM: Double = 0.05,
        shootingAngleDeg: Double = 0.0,
        windSpeedMs: Double = 0.0,
        windAngleDeg: Double = 0.0,
    ): BallisticsResult
}