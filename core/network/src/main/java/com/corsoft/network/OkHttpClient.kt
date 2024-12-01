package com.corsoft.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal fun createOkHttpClient(
    authInterceptor: Interceptor,
    chuckerInterceptor: ChuckerInterceptor,
    isProdReleaseFlavor: Boolean
): OkHttpClient {
    return OkHttpClient.Builder().run {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        addInterceptor(authInterceptor)
        addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })

        if (!isProdReleaseFlavor) {
            addInterceptor(chuckerInterceptor)
        }
        build()

    }
}