package com.corsoft.hitfactor

import android.app.Application
import com.corsoft.auth.di.authDataModule
import com.corsoft.auth.di.authFeatureModule
import com.corsoft.common.di.commonModule
import com.corsoft.data.di.dataModule
import com.corsoft.hitfactor.data.user.di.userDataModule
import com.corsoft.hitfactor.di.appModule
import com.corsoft.network.di.networkModule
import com.corsoft.services.di.servicesFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

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
        val coreModules = module { includes(networkModule, dataModule, commonModule) }
        val dataModules = module { includes(authDataModule, userDataModule) }
        val featureModules = module { includes(authFeatureModule, servicesFeatureModule) }
        modules(appModule, coreModules, dataModules, featureModules)
    }


}