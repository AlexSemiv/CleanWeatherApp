package com.example.remote.other

import retrofit2.Response
import java.io.IOException
import java.lang.IllegalStateException

class ApiLimitException(message: String): IOException(message)
class InvalidFormatException(message: String): IOException(message)
class InvalidApiKeyException(message: String): IOException(message)
class ServiceOfflineException(message: String): IOException(message)
class InternalErrorException(message: String): IOException(message)
class TimeoutRequestException(message: String): IOException(message)

object SafeApiRequest {
    private const val SUCCESS_200 = 200
    private const val UNSUCCESS_429 = 429
    private const val UNSUCCESS_422 = 422
    private const val UNSUCCESS_401 = 401
    private const val UNSUCCESS_404 = 404
    private const val UNSUCCESS_503 = 503
    private const val UNSUCCESS_500 = 500
    private const val UNSUCCESS_504 = 504

    suspend fun <T> handleApiRequest(call: suspend () -> Response<T>): T? {
        val response = call.invoke()

        return if (response.isSuccessful) {
            when (response.code()) {
                SUCCESS_200 -> response.body()!!
                else -> null
            }
        } else {
            when (response.code()) {
                UNSUCCESS_429 -> throw ApiLimitException("API limit reached.\nPlease try again later.")
                UNSUCCESS_422 -> throw InvalidFormatException("Your request parameters are incorrect.")
                UNSUCCESS_401 -> throw InvalidApiKeyException("Access to your account has been suspended, contact TMDb.")
                UNSUCCESS_503 -> throw ServiceOfflineException("Access to your account has been suspended, contact TMDb.")
                UNSUCCESS_500 -> throw InternalErrorException("Access to your account has been suspended, contact TMDb.")
                UNSUCCESS_504 -> throw TimeoutRequestException("Your request to the backend server timed out. Try again.")
                UNSUCCESS_404 -> throw TimeoutRequestException("No results for your query. Try again.")
                else -> throw IllegalStateException()
            }
        }
    }
}