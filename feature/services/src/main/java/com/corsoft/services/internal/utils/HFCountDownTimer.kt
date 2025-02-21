package com.corsoft.services.internal.utils

import android.os.CountDownTimer

class HFCountDownTimer(
    duration: Int,
    delay: Int,
    private val onTimerTick: (Long) -> Unit,
    private val onTimerFinish: () -> Unit
) : CountDownTimer(duration.toLong(), delay.toLong()) {

    override fun onTick(millisUntilFinished: Long) {
        onTimerTick(millisUntilFinished)
    }

    override fun onFinish() {
        onTimerFinish()
    }
}