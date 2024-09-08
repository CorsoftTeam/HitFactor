package com.corsoft.network.di

import com.corsoft.network.createOkHttpClient
import com.corsoft.network.createRetrofit
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import ppk.app.core.network.internal.interceptor.AuthInterceptor

val networkModule = module {
    single { createOkHttpClient(listOf(AuthInterceptor(get()), HttpLoggingInterceptor())) }
    single { createRetrofit(get()) }
}