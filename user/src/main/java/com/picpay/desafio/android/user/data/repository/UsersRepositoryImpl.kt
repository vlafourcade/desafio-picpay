package com.picpay.desafio.android.user.data.repository

import com.picpay.desafio.android.core.data.local.LocalStorage
import com.picpay.desafio.android.core.utils.logging.Logger
import com.picpay.desafio.android.user.data.local.UsersDao
import com.picpay.desafio.android.user.data.mapper.toDto
import com.picpay.desafio.android.user.data.mapper.toEntity
import com.picpay.desafio.android.user.data.remote.UsersApi
import com.picpay.desafio.android.user.domain.model.dto.UserDto
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import javax.inject.Inject

@Suppress("VariableNaming")
internal class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao,
    private val localStorage: LocalStorage,
    private val logger: Logger
) : UsersRepository() {
    private val USER_UPDATE_TIME_STORAGE_KEY: String = "USER_UPDATE_TIME_STORAGE_KEY"

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getContacts(
        forceUpdate: Boolean,
        ignoreApiErrors: Boolean
    ): List<UserDto>? {

        if (forceUpdate) {
            try {
                val result = api.getUsers()?.map { it.toEntity() }
                result?.let {
                    dao.insertAll(it)
                    localStorage.put(USER_UPDATE_TIME_STORAGE_KEY, System.currentTimeMillis())
                }
            } catch (throwable: Throwable) {
                logger.e(throwable.localizedMessage, throwable)

                if (ignoreApiErrors.not()) throw parseApiError(throwable)
            }
        }

        val result = dao.getAll()

        return result.map { it.toDto() }
    }

    override suspend fun getLatestRefreshTime(): Long =
        localStorage.get(USER_UPDATE_TIME_STORAGE_KEY, Long::class.java) ?: 0
}
