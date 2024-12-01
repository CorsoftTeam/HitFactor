package com.corsoft.hitfactor.provider

import com.corsoft.hitfactor.BuildConfig
import com.vaskorr.provider.CoreNetworkConfigProvider
import org.koin.core.component.KoinComponent

private const val APP_PROD = "appProd"
private const val BUILD_TYPE_RELEASE = "release"

class HFCoreNetworkConfigProvideImpl : CoreNetworkConfigProvider, KoinComponent {

    override fun isProdReleaseFlavor(): Boolean {
        return BuildConfig.BUILD_TYPE == BUILD_TYPE_RELEASE
    }

}