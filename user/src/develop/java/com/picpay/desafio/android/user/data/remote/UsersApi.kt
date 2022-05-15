package com.picpay.desafio.android.user.data.remote

import com.picpay.desafio.android.user.data.model.UserResponse
import kotlinx.coroutines.delay

internal interface UsersApi {
    suspend fun getUsers(): List<UserResponse>?
}

internal class UsersMockApi : UsersApi {
    override suspend fun getUsers(): List<UserResponse>? {
        delay(20000)

        val result = mutableListOf<UserResponse>()

        with(result) {
            for (i in 0..50) {
                add(
                    UserResponse(
                        name = "User Name #$i",
                        username = "username$i",
                        img = null,
                        id = i
                    )
                )
            }
        }

        return result
    }
}