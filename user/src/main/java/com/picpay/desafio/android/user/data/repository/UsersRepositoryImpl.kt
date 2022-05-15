package com.picpay.desafio.android.user.data.repository

import com.picpay.desafio.android.core.data.local.LocalStorage
import com.picpay.desafio.android.user.data.local.UsersDao
import com.picpay.desafio.android.user.data.mapper.toDto
import com.picpay.desafio.android.user.data.mapper.toEntity
import com.picpay.desafio.android.user.data.remote.UsersApi
import com.picpay.desafio.android.user.domain.model.dto.UserDto
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao,
    private val localStorage: LocalStorage
) : UsersRepository {
    private val USER_UPDATE_TIME_STORAGE_KEY: String = "USER_UPDATE_TIME_STORAGE_KEY"

    override suspend fun getContacts(forceUpdate: Boolean): List<UserDto>? {
        try {
            if (forceUpdate) {
                val result = api.getUsers()?.map { it.toEntity() }
                result?.let {
                    dao.insertAll(it)
                    localStorage.put(USER_UPDATE_TIME_STORAGE_KEY, System.currentTimeMillis())
                }
            }

            val result = dao.getAll()

            return result.map { it.toDto() }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }

    override suspend fun getLatestRefreshTime(): Long =
        localStorage.get(USER_UPDATE_TIME_STORAGE_KEY, Long::class.java) ?: 0
}