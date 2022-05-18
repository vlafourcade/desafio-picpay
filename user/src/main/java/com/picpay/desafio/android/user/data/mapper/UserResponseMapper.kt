package com.picpay.desafio.android.user.data.mapper

import com.picpay.desafio.android.user.data.model.UserEntity
import com.picpay.desafio.android.user.data.model.UserResponse

internal fun UserResponse.toEntity() = UserEntity(
    id = this.id,
    name = this.name,
    username = this.username,
    img = this.img
)
