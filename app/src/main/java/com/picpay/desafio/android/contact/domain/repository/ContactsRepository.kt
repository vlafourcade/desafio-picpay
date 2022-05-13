package com.picpay.desafio.android.contact.domain.repository

import com.picpay.desafio.android.contact.domain.model.dto.ContactDto
import kotlin.jvm.Throws

internal interface ContactsRepository {
    @Throws(Throwable::class)
    suspend fun getContacts(forceUpdate: Boolean = false) : List<ContactDto>?
}