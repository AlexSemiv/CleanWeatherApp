package com.example.cleanweatherapp.common.other

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T? = null, message: String): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
    object Empty: Resource<Nothing>()
}