package com.corsoft.hitfactor.di

import com.corsoft.hitfactor.app.AppViewModel
import com.corsoft.hitfactor.provider.HFCoreNetworkConfigProvideImpl
import com.vaskorr.provider.CoreNetworkConfigProvider
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AppViewModel)
    factoryOf(::HFCoreNetworkConfigProvideImpl) bind CoreNetworkConfigProvider::class
}