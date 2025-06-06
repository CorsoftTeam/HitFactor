package com.corsoft.hitfactor.data.user.internal.network

import com.corsoft.hitfactor.data.user.internal.network.model.request.AddGunRequest
import com.corsoft.hitfactor.data.user.internal.network.model.response.GunItemResponse
import com.corsoft.hitfactor.data.user.internal.network.model.response.ProfilePhotoResponse
import com.corsoft.hitfactor.data.user.internal.network.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface UserApi {

    @GET("users/{userId}/guns")
    suspend fun getMyGuns(
        @Path("userId") userId: String
    ): Response<List<GunItemResponse>>

    @GET("users/get_me")
    suspend fun getMe(): Response<UserResponse>

    @POST("users/{userId}/guns")
    suspend fun addGun(
        @Path("userId") userId: String,
        @Body body: AddGunRequest
    ): Response<Unit>

    @GET("users/{userId}/guns/{gunId}")
    suspend fun getGunById(
        @Path("userId") userId: String,
        @Path("gunId") gunId: Int
    ): Response<GunItemResponse>

    @DELETE("users/{userId}/guns/{gunId}")
    suspend fun deleteGunById(
        @Path("userId") userId: String,
        @Path("gunId") gunId: Int
    ): Response<GunItemResponse>

    @GET("users/{userId}/image")
    suspend fun getProfilePhoto(
        @Path("userId") userId: String,
    ): Response<ProfilePhotoResponse>
}