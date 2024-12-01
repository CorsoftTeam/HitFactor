package com.corsoft.data.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.corsoft.data.createEncryptedSharedPreferences
import com.corsoft.data.storage.EncryptedStorage
import com.corsoft.data.storage.EncryptedStorageImpl

val dataModule = module {
    single { createEncryptedSharedPreferences(androidContext()) }
    singleOf(::EncryptedStorageImpl) bind EncryptedStorage::class
}