package com.corsoft.hitfactor.data.analytics.api

import android.os.Bundle

interface AnalyticsRepository {
    fun sendEvent(key: String, params: Bundle? = null)
}