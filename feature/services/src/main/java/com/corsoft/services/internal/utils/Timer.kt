package com.corsoft.services.internal.utils

import org.koin.mp.KoinPlatformTimeTools
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class Timer {

    val start: Duration = getNowTime()

    private fun getNowTime(): Duration {
        return KoinPlatformTimeTools.getTimeInNanoSeconds().toDuration(DurationUnit.NANOSECONDS)
    }

    fun getTimeInMillis() = (getNowTime() - start).toDouble(DurationUnit.MILLISECONDS).roundToInt()
}