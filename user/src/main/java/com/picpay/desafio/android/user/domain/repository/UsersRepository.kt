package com.picpay.desafio.android.user.domain.repository

import com.picpay.desafio.android.core.data.model.ApiError
import com.picpay.desafio.android.core.data.repository.BaseRepository
import com.picpay.desafio.android.user.domain.model.dto.UserDto

internal abstract class UsersRepository : BaseRepository() {
    @Throws(ApiError::class)
    abstract suspend fun getContacts(
        forceUpdate: Boolean = false,
        ignoreApiErrors: Boolean = true
    ): List<UserDto>?

    abstract suspend fun getLatestRefreshTime(): Long
}