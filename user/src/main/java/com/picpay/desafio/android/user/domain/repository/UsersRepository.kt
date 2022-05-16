package com.picpay.desafio.android.user.domain.repository

import com.picpay.desafio.android.core.data.repository.BaseCachedRepository
import com.picpay.desafio.android.user.data.model.UserEntity
import com.picpay.desafio.android.user.data.model.UserResponse
import com.picpay.desafio.android.user.domain.model.dto.UserDto

abstract class UsersRepository :
    BaseCachedRepository<List<UserResponse>?, List<UserEntity>?, List<UserDto>?>()