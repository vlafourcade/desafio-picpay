package com.picpay.desafio.android.user.data.remote

import com.picpay.desafio.android.user.data.model.UserResponse
import retrofit2.http.GET

internal interface UsersApi {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>?
}