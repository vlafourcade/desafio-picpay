package com.picpay.desafio.android.user.domain.repository

import com.picpay.desafio.android.user.domain.model.dto.UserDto
import kotlin.jvm.Throws

internal interface UsersRepository {
    @Throws(Throwable::class)
    suspend fun getContacts(forceUpdate: Boolean = false): List<UserDto>?
}