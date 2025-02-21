package com.corsoft.auth.internal

import com.corsoft.auth.api.AuthRepository
import com.corsoft.auth.internal.network.AuthApi
import com.corsoft.auth.internal.network.model.request.RegisterRequest
import com.corsoft.auth.internal.network.model.request.UserField
import com.corsoft.network.model.NetworkResponse
import com.corsoft.data.storage.EncryptedStorage
import ppk.app.core.network.util.apiCall
import ppk.app.core.network.util.doOn

internal class AuthRepositoryImpl(
    private val storage: EncryptedStorage,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(login: String, password: String): NetworkResponse<Unit> =
        apiCall {
            authApi.login(
                login = login,
                password = password
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
        email: String,
        name: String
    ): NetworkResponse<Unit> =
        apiCall {
            authApi.register(
                request = RegisterRequest(
                    user = UserField(
                        login = login,
                        password = password,
                        email = email,
                        name = name
                    )
                )
            )
        }.doOn(
            success = {
                NetworkResponse.Success(Unit)
            },
            failed = { it }
        )

}