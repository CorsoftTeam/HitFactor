package com.corsoft.hitfactor.data.user.api

import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.network.model.NetworkResponse

interface UserRepository {
    suspend fun getMyGuns(): NetworkResponse<List<Gun>>
    suspend fun getMe(): NetworkResponse<User>
}