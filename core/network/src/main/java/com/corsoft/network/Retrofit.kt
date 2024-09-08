package com.corsoft.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

internal fun createRetrofit(client: OkHttpClient): Retrofit {
    val json = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder().run {
        baseUrl("https://176.119.158.108/api/")
        addConverterFactory(json.asConverterFactory(MediaType.APPLICATION_JSON.value.toMediaType()))
        client(client)
        build()
    }
}