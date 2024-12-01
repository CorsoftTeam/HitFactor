package com.corsoft.network.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.corsoft.network.createOkHttpClient
import com.corsoft.network.createRetrofit
import com.vaskorr.provider.CoreNetworkConfigProvider
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import ppk.app.core.network.internal.interceptor.AuthInterceptor

val networkModule = module {
    single {
        createOkHttpClient(
            authInterceptor = AuthInterceptor(get()),
            chuckerInterceptor = ChuckerInterceptor(get()),
            isProdReleaseFlavor = get<CoreNetworkConfigProvider>().isProdReleaseFlavor()
        )
    }
    single { createRetrofit(get()) }
}