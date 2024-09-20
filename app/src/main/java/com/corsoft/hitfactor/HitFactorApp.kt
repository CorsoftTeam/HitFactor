package com.corsoft.hitfactor

import android.app.Application
import com.corsoft.auth.di.authDataModule
import com.corsoft.auth.di.authFeatureModule
import com.corsoft.hitfactor.di.appModule
import com.corsoft.network.di.networkModule
import com.corsoft.services.di.servicesFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import ppk.app.core.data.di.dataModule

class HitFactorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.DEBUG)
            addModules()
        }
    }

    private fun KoinApplication.addModules() {
        val coreModules = module { includes(networkModule, dataModule) }
        val dataModules = module { includes(authDataModule) }
        val featureModules = module { includes(authFeatureModule, servicesFeatureModule) }
        modules(appModule, coreModules, dataModules, featureModules)
    }


}