package ppk.app.core.data.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ppk.app.core.data.createEncryptedSharedPreferences
import ppk.app.core.data.storage.EncryptedStorage
import ppk.app.core.data.storage.EncryptedStorageImpl

val dataModule = module {
    single { createEncryptedSharedPreferences(androidContext()) }
    singleOf(::EncryptedStorageImpl) bind EncryptedStorage::class
}