package ppk.app.core.network.util

import android.webkit.MimeTypeMap
import com.corsoft.network.model.NetworkResponse
import com.corsoft.network.model.ServerError
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

private const val UNKNOWN_ERROR = "Неизвестная ошибка"
private const val EMPTY_BODY = "Empty body"

fun File.toMultipartBody(name: String, fileName: String = this.name): MultipartBody.Part {
    val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(this.extension)
    val documentMediaType = mimeType?.toMediaType() ?: throw IllegalStateException()
    val documentBody = this.asRequestBody(documentMediaType)
    return MultipartBody.Part.createFormData(name, fileName, documentBody)
}


@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = true
}

fun Throwable.displayedError(): String {
    return localizedMessage ?: UNKNOWN_ERROR
}

inline fun <Body, Result> NetworkResponse<Body>.doOn(
    success: (Body) -> Result,
    failed: (NetworkResponse.Failed) -> Result
): Result {
    return when (this) {
        is NetworkResponse.Success -> success(data)
        is NetworkResponse.Failed -> failed(this)
    }
}

suspend fun <ApiResponse> apiCall(call: suspend () -> Response<ApiResponse>): NetworkResponse<ApiResponse> {
    return try {
        val response = call()
        return if (response.isSuccessful) {
            val responseBody =
                response.body() ?: return NetworkResponse.Failed(Throwable(EMPTY_BODY))
            NetworkResponse.Success(responseBody)
        } else {
            handleErrorResponse(response)
        }
    } catch (e: Exception) {
        NetworkResponse.Failed(e)
    }
}

private fun <ApiResponse> handleErrorResponse(
    response: Response<ApiResponse>
): NetworkResponse<ApiResponse> {
    val errorString = response.errorBody()?.string()
        ?: return NetworkResponse.Failed(Throwable(UNKNOWN_ERROR))
    return try {
        val serverError = json.decodeFromString<ServerError>(errorString)
        val errorCode = serverError.statusCode
        NetworkResponse.Failed(Throwable(serverError.message), errorCode)
    } catch (e: Exception) {
        NetworkResponse.Failed(e)
    }
}
