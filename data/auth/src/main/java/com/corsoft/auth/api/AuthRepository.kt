package com.corsoft.auth.api

import com.corsoft.network.model.NetworkResponse

interface AuthRepository {
    suspend fun login(login: String, password: String): NetworkResponse<Unit>
    suspend fun register(login: String, password: String, email: String, name: String): NetworkResponse<Unit>
    fun isUserAuthorised(): Boolean
}