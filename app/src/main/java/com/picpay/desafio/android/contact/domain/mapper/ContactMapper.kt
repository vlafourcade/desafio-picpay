package com.picpay.desafio.android.contact.domain.mapper

import com.picpay.desafio.android.contact.domain.model.Contact
import com.picpay.desafio.android.contact.domain.model.dto.ContactDto

internal fun ContactDto.toModel() = Contact(
    id = this.id,
    name = this.name,
    username = this.username,
    img = this.img
)