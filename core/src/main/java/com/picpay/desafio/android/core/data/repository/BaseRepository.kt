package com.picpay.desafio.android.core.data.repository

import com.picpay.desafio.android.core.data.model.ApiError
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {
    protected fun parseApiError(throwable: Throwable): ApiError {
        return when (throwable) {
            is IOException -> ApiError.InternetUnavailable
            is HttpException -> {
                when (throwable.code()) {
                    400 -> ApiError.BadRequest
                    401 -> ApiError.Unauthorized
                    404 -> ApiError.NotFound
                    500 -> ApiError.ServiceUnavailable
                    else -> ApiError.Unknown
                }
            }
            else -> ApiError.Unknown
        }
    }
}