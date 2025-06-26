package com.corsoft.auth.api

import com.corsoft.network.model.NetworkResponse

interface AuthRepository {
    suspend fun login(email: String, password: String): NetworkResponse<Unit>
    suspend fun register(login: String, password: String, email: String, name: String): NetworkResponse<Unit>
    suspend fun isUserAuthorised(): Boolean
    suspend fun isUserVip(): Boolean
}