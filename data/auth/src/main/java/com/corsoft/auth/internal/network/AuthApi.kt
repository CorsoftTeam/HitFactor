package com.corsoft.auth.internal.network

import com.corsoft.auth.internal.network.model.request.RegisterRequest
import com.corsoft.auth.internal.network.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface AuthApi {
    @POST("users")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Unit>

    @GET("authorization")
    suspend fun login(
        @Query("login") login: String,
        @Query("password") password: String
    ): Response<LoginResponse>
}