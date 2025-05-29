package com.corsoft.network

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal fun createRetrofit(client: OkHttpClient): Retrofit {
    val json = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder().run {
        baseUrl("http://176.108.252.100:3002/api/")
        addConverterFactory(GsonConverterFactory.create())
        client(client)
        build()
    }
}