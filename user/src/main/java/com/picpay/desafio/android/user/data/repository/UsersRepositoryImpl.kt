package com.picpay.desafio.android.user.data.repository

import com.picpay.desafio.android.core.data.local.LocalStorage
import com.picpay.desafio.android.user.data.local.UsersDao
import com.picpay.desafio.android.user.data.mapper.toDto
import com.picpay.desafio.android.user.data.mapper.toEntity
import com.picpay.desafio.android.user.data.model.UserEntity
import com.picpay.desafio.android.user.data.model.UserResponse
import com.picpay.desafio.android.user.data.remote.UsersApi
import com.picpay.desafio.android.user.domain.model.dto.UserDto
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import javax.inject.Inject

internal class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApi,
    private val dao: UsersDao,
    private val localStorage: LocalStorage
) : UsersRepository() {
    private val USER_UPDATE_TIME_STORAGE_KEY: String = "USER_UPDATE_TIME_STORAGE_KEY"

    override suspend fun fetchRemoteData(): List<UserResponse>? = api.getUsers()

    override suspend fun fetchLocalData(): List<UserEntity> = dao.getAll()

    override suspend fun saveLocalData(localData: List<UserEntity>?) {
        localData?.let { dao.insertAll(it) }
    }

    override fun parseToLocalEntity(remoteData: List<UserResponse>?): List<UserEntity>? =
        remoteData?.map { it.toEntity() }

    override fun parseToDto(localData: List<UserEntity>?): List<UserDto>? =
        localData?.map { it.toDto() }

    override suspend fun updateLatestRefreshTime() {
        localStorage.put(USER_UPDATE_TIME_STORAGE_KEY, System.currentTimeMillis())
    }

    override fun getLatestRefreshTime(): Long =
        localStorage.get(USER_UPDATE_TIME_STORAGE_KEY, Long::class.java) ?: 0L
}