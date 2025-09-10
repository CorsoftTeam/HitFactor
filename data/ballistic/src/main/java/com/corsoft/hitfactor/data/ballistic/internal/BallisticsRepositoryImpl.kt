package com.corsoft.hitfactor.data.ballistic.internal

import com.corsoft.hitfactor.data.ballistic.api.BallisticsRepository
import com.corsoft.hitfactor.data.ballistic.api.model.BallisticsResult
import com.corsoft.hitfactor.data.ballistic.internal.mapper.fpsToMs
import com.corsoft.hitfactor.data.ballistic.internal.mapper.inchesToCentimeters
import com.corsoft.hitfactor.data.ballistic.internal.mapper.metersToInches
import com.corsoft.hitfactor.data.ballistic.internal.mapper.metersToYards
import com.corsoft.hitfactor.data.ballistic.internal.mapper.msToFps
import com.corsoft.hitfactor.data.ballistic.internal.mapper.yardsToMeters

internal class BallisticsRepositoryImpl : BallisticsRepository {

    external fun nativeSolve(
        bc: Double,
        mvFps: Double,
        sightHeightFt: Double,
        angleDeg: Double,
        zeroRangeYd: Double,
        windSpeedFps: Double,
        windAngleDeg: Double,
        queryRangeYd: Double
    ): DoubleArray

    override fun solve(
        ballisticCoefficient: Double,
        muzzleVelocityMs: Double,
        zeroRangeM: Double,
        rangeM: Double,
        sightHeightM: Double,
        shootingAngleDeg: Double,
        windSpeedMs: Double,
        windAngleDeg: Double
    ): BallisticsResult {
        val mvFps = msToFps(muzzleVelocityMs)
        val sightHeightFt = metersToInches(sightHeightM)
        val zeroRangeYd = metersToYards(zeroRangeM)
        val windFps = msToFps(windSpeedMs)
        val queryRangeYd = metersToYards(rangeM)

        val result = nativeSolve(
            ballisticCoefficient,
            mvFps,
            sightHeightFt,
            shootingAngleDeg,
            zeroRangeYd,
            windFps,
            windAngleDeg,
            queryRangeYd
        )

        return BallisticsResult(
            rangeM = yardsToMeters(result[0]),
            pathM = inchesToCentimeters(result[1]),
            windage = inchesToCentimeters(result[2]),
            speed = fpsToMs(result[3]),
            time = result[4]
        )
    }

    companion object {
        init {
            System.loadLibrary("ballistics_wrapper")
        }
    }
}