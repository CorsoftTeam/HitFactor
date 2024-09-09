package com.corsoft.hitfactor.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.corsoft.hitfactor.app.App
import com.corsoft.ui.theme.HitFactorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HitFactorTheme {
                App()
            }
        }
    }
}