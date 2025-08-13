package com.corsoft.hitfactor.data.analytics.di

import com.corsoft.hitfactor.data.analytics.api.AnalyticsRepository
import com.corsoft.hitfactor.data.analytics.internal.AnalyticsRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsDataModule = module {
    single {
        AnalyticsRepositoryImpl(get())
    } bind AnalyticsRepository::class
}