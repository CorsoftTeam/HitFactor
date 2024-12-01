package com.corsoft.network

import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal fun createRetrofit(client: OkHttpClient): Retrofit {
    val json = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder().run {
        baseUrl("http://176.119.158.108:3002/api/")
        addConverterFactory(GsonConverterFactory.create())
        client(client)
        build()
    }
}