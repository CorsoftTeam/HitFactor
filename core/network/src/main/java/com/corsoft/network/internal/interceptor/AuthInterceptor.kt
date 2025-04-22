package com.corsoft.network.internal.interceptor

import com.corsoft.data.storage.EncryptedStorage
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val storage: EncryptedStorage) : Interceptor {
    companion object {
        private const val HEADER_ACCEPT = "Accept"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "*/*"
        private const val CONTENT_TYPE_MULTIPART_FORM_DATA = "application/json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().run {
            addHeader(HEADER_ACCEPT, CONTENT_TYPE_JSON)
            storage.accessToken?.let { token ->
                addHeader(HEADER_AUTHORIZATION, token)
            }
            storage.cookie?.let { cookie ->
                addHeader("Cookie", cookie)
            }
            addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_MULTIPART_FORM_DATA)
            build()
        }.let(chain::proceed)
    }
}