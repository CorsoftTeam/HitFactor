package com.corsoft.hitfactor.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.corsoft.hitfactor.app.App
import com.corsoft.ui.theme.HitFactorTheme
import org.koin.android.ext.android.inject
import ru.rustore.sdk.billingclient.RuStoreBillingClient

class MainActivity : ComponentActivity() {

    val billingClient: RuStoreBillingClient by inject<RuStoreBillingClient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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