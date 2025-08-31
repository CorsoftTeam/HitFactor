package com.corsoft.hitfactor.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.corsoft.hitfactor.app.App
import com.corsoft.ui.theme.HitFactorTheme
import com.google.firebase.FirebaseApp
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import org.koin.android.ext.android.inject
import ru.rustore.sdk.billingclient.RuStoreBillingClient

class MainActivity : ComponentActivity() {

    private val billingClient: RuStoreBillingClient by inject<RuStoreBillingClient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val config = AppMetricaConfig
            .newConfigBuilder("d00294cf-dff0-454f-856a-45b79c51cc7a")
            .build()
        AppMetrica.activate(this, config)
        if (savedInstanceState == null) {
            billingClient.onNewIntent(intent)
        }
        enableEdgeToEdge()
        setContent {
            HitFactorTheme {
                App()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        billingClient.onNewIntent(intent)
    }
}