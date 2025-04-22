package com.corsoft.hitfactor.data.user.internal.network

import com.corsoft.hitfactor.data.user.internal.network.model.response.GunItemResponse
import com.corsoft.hitfactor.data.user.internal.network.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface UserApi {

    @GET("users/{userId}/guns")
    suspend fun getMyGuns(
        @Path("userId") userId: String
    ): Response<List<GunItemResponse>>

    @GET("users/get_me")
    suspend fun getMe(): Response<UserResponse>
}