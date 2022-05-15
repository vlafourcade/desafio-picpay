package com.picpay.desafio.android.user.domain.mapper

import com.picpay.desafio.android.user.domain.model.User
import com.picpay.desafio.android.user.domain.model.dto.UserDto

internal fun UserDto.toModel() = User(
    id = this.id,
    name = this.name,
    username = "@${this.username}",
    img = this.img
)