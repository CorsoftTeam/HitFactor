package ppk.app.core.network.internal.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ppk.app.core.data.storage.EncryptedStorage

internal class AuthInterceptor(private val storage: EncryptedStorage) : Interceptor {
    companion object {
        private const val HEADER_ACCEPT = "accept"
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_CONTENT_TYPE = "Content-Type"
        private const val CONTENT_TYPE_JSON = "application/json"
        private const val CONTENT_TYPE_MULTIPART_FORM_DATA = "multipart/form-data"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().run {
            addHeader(HEADER_ACCEPT, CONTENT_TYPE_JSON)
            storage.accessToken?.let { token ->
                addHeader(HEADER_AUTHORIZATION, "Bearer $token")
            }
            addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_MULTIPART_FORM_DATA)
            build()
        }.let(chain::proceed)
    }
}