package com.picpay.desafio.android.user.data.mapper

import com.picpay.desafio.android.user.data.model.UserEntity
import com.picpay.desafio.android.user.domain.model.dto.UserDto

internal fun UserEntity.toDto() = UserDto(
    id = this.id,
    name = this.name,
    username = this.username,
    img = this.img
)
