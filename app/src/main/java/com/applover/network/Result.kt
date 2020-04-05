package com.applover.network

import com.applover.common.NO_INTERNET_CONNECTION
import org.koin.core.context.GlobalContext.get

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

inline fun <T> safeCall(call: () -> T): Result<T> =
    try {
        val connectivityManager: ConnectionManagerImpl = get().get()
        when (connectivityManager.hasNetworkConnection()) {
            true -> Result.Success(call())
            else -> throw Exception(NO_INTERNET_CONNECTION)
        }
    } catch (exception: Exception) {
        Result.Failure(exception)
    }
