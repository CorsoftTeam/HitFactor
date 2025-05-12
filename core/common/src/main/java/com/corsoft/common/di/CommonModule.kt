package com.corsoft.common.di

import com.corsoft.common.ResourceProvider
import com.corsoft.common.ResourceProviderImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    factoryOf(::ResourceProviderImpl) bind ResourceProvider::class
}