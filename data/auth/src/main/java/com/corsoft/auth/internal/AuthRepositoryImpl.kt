package com.corsoft.auth.internal

import com.corsoft.auth.api.AuthRepository
import com.corsoft.auth.internal.network.AuthApi
import com.corsoft.auth.internal.network.model.request.LoginRequest
import com.corsoft.network.model.NetworkResponse
import ppk.app.core.data.storage.EncryptedStorage
import ppk.app.core.network.util.apiCall
import ppk.app.core.network.util.doOn

internal class AuthRepositoryImpl(
    private val storage: EncryptedStorage,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(login: String, password: String): NetworkResponse<Unit> =
        apiCall {
            authApi.login(
                request = LoginRequest(
                    login = login,
                    password = password
                )
            )
        }.doOn(
            success = {
                storage.accessToken = it.accessToken
                NetworkResponse.Success(Unit)
            },
            failed = { it }
        )

    override suspend fun register(
        login: String,
        password: String,
        phone: String
    ): NetworkResponse<Unit> {
        TODO("Not yet implemented")
    }

}