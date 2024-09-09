package com.corsoft.auth.internal.network

import com.corsoft.auth.internal.network.model.request.LoginRequest
import com.corsoft.auth.internal.network.model.request.RegisterRequest
import com.corsoft.auth.internal.network.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApi {
    @POST("v1/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<LoginResponse>

    @POST("v1/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}