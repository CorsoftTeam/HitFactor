package com.corsoft.auth.di

import com.corsoft.auth.api.AuthRepository
import com.corsoft.auth.internal.AuthRepositoryImpl
import com.corsoft.auth.internal.network.AuthApi
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val authDataModule = module {
    factory { get<Retrofit>().create<AuthApi>() }
    single {
        AuthRepositoryImpl(
            storage = get(),
            authApi = get()
        )
    } bind AuthRepository::class
}