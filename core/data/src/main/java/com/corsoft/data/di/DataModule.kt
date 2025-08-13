package com.corsoft.data.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.corsoft.data.internal.createEncryptedSharedPreferences
import com.corsoft.data.internal.LocalStorageImpl
import com.corsoft.data.api.storage.EncryptedStorage
import com.corsoft.data.api.storage.EncryptedStorageImpl
import com.corsoft.data.api.storage.LocalStorage

val dataModule = module {
    single { createEncryptedSharedPreferences(androidContext()) }
    singleOf(::EncryptedStorageImpl) bind EncryptedStorage::class
    singleOf(::LocalStorageImpl) bind LocalStorage::class
}