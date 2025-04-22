package com.corsoft.hitfactor.data.user.internal

import android.util.Log
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.hitfactor.data.user.internal.mapper.toModel
import com.corsoft.hitfactor.data.user.internal.network.UserApi
import com.corsoft.network.model.NetworkResponse
import ppk.app.core.network.util.apiCall
import ppk.app.core.network.util.doOn

internal class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getMyGuns(): NetworkResponse<List<Gun>> =
        apiCall {
            userApi.getMyGuns(
                userId = userApi.getMe().body()?.uuid ?: ""
            )
        }.doOn(
            success = {
                NetworkResponse.Success(it.map { gunItem -> gunItem.toModel() })
            },
            failed = {
                Log.d("what", it.getErrorMessage())
                it
            }
        )

    override suspend fun getMe(): NetworkResponse<User> =
        apiCall {
            userApi.getMe()
        }.doOn(
            success = {
                NetworkResponse.Success(it.toModel())
            },
            failed = { it }
        )

}