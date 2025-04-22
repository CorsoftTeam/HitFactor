package com.corsoft.auth.internal

import android.util.Log
import com.corsoft.auth.api.AuthRepository
import com.corsoft.auth.internal.network.AuthApi
import com.corsoft.auth.internal.network.model.request.AuthRequest
import com.corsoft.auth.internal.network.model.request.RegisterRequest
import com.corsoft.auth.internal.network.model.request.UserField
import com.corsoft.data.storage.EncryptedStorage
import com.corsoft.network.model.NetworkResponse
import ppk.app.core.network.util.apiCall
import ppk.app.core.network.util.doOn

internal class AuthRepositoryImpl(
    private val storage: EncryptedStorage,
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(login: String, password: String): NetworkResponse<Unit> {
        val response = authApi.login(
            AuthRequest(
                login = login,
                password = password
            )
        )
        if (response.isSuccessful) {
            Log.d("HEADER", response.headers()["set-cookie"].toString())
            storage.accessToken = response.body()?.token
            storage.cookie = response.headers()["set-cookie"]
            return NetworkResponse.Success(Unit)
        } else {
            return NetworkResponse.Failed(Throwable(response.errorBody()?.string()))
        }
    }

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

    override fun isUserAuthorised(): Boolean {
        return !storage.accessToken.isNullOrEmpty()
    }

}