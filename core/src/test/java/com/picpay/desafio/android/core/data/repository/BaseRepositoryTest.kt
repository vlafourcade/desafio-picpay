package com.picpay.desafio.android.core.data.repository

import android.os.Build
import com.picpay.desafio.android.core.data.model.ApiError
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.LOLLIPOP, Build.VERSION_CODES.P])
class BaseRepositoryTest {
    private class MockedClass: BaseRepository()

    private val baseRepository = MockedClass()

    @Test
    fun parseUnknownErrorTest() {
        val result = baseRepository.parseApiError(Throwable())

        Assert.assertTrue(result is ApiError.Unknown)
    }

    @Test
    fun parseBadRequestErrorTest() {
        val result = baseRepository.parseApiError(
            HttpException(
                Response.error<ResponseBody>(
                    400,
                    "content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        )

        Assert.assertTrue(result is ApiError.BadRequest)
    }

    @Test
    fun parseUnauthorizedErrorTest() {
        val result = baseRepository.parseApiError(
            HttpException(
                Response.error<ResponseBody>(
                    401,
                    "content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        )

        Assert.assertTrue(result is ApiError.Unauthorized)
    }

    @Test
    fun parseNotFoundErrorTest() {
        val result = baseRepository.parseApiError(
            HttpException(
                Response.error<ResponseBody>(
                    404,
                    "content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        )

        Assert.assertTrue(result is ApiError.NotFound)
    }

    @Test
    fun parseServiceUnavailableErrorTest() {
        val result = baseRepository.parseApiError(
            HttpException(
                Response.error<ResponseBody>(
                    500,
                    "content".toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        )

        Assert.assertTrue(result is ApiError.ServiceUnavailable)
    }

    @Test
    fun parseInternetUnavailableErrorTest() {
        val result = baseRepository.parseApiError(IOException())

        Assert.assertTrue(result is ApiError.InternetUnavailable)
    }
}