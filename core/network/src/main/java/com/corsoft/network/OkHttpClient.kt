package com.corsoft.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal fun createOkHttpClient(interceptors: List<Interceptor>): OkHttpClient {
    return OkHttpClient.Builder().run {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        interceptors.forEach { interceptor ->
            addInterceptor(interceptor)
        }
        build()
    }
}