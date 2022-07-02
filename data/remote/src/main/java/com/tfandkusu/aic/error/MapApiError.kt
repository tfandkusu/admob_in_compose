package com.tfandkusu.aic.error

import retrofit2.HttpException
import java.io.IOException

fun mapApiError(e: Throwable): Throwable {
    return when (e) {
        is IOException -> {
            NetworkErrorException()
        }
        is HttpException -> {
            ServerErrorException(e.code(), e.message())
        }
        else -> {
            UnknownErrorException()
        }
    }
}
