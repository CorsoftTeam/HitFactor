package com.corsoft.hitfactor.data.analytics.internal

import android.os.Bundle
import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import io.appmetrica.analytics.AppMetrica

internal class AnalyticsRepositoryImpl(
    private val analytics: FirebaseAnalytics
) : AnalyticsRepository {
    override fun sendEvent(key: String, params: Bundle?) {
        analytics.logEvent(key, params)
        AppMetrica.reportEvent(key)
    }
}