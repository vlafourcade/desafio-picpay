package com.picpay.desafio.android.core.data.model

sealed class ApiError : Throwable() {
    object InternetUnavailable : ApiError()

    object BadRequest : ApiError()

    object NotFound : ApiError()

    object Unauthorized : ApiError()

    object ServiceUnavailable : ApiError()

    object Unknown : ApiError()
}
