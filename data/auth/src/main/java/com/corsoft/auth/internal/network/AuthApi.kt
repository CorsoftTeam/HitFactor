package com.corsoft.auth.internal.network

import com.corsoft.auth.internal.network.model.request.AuthRequest
import com.corsoft.auth.internal.network.model.request.RegisterRequest
import com.corsoft.auth.internal.network.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApi {
    @POST("users")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<Unit>

    @POST("users/authorization")
    suspend fun login(
        @Body request: AuthRequest
    ): Response<LoginResponse>
}