package com.corsoft.hitfactor.data.ballistic.di

import com.corsoft.hitfactor.data.ballistic.api.BallisticsRepository
import com.corsoft.hitfactor.data.ballistic.internal.BallisticsRepositoryImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val ballisticsDataModule = module {
    single { BallisticsRepositoryImpl() } bind BallisticsRepository::class
}