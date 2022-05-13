package com.picpay.desafio.android.contact.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    val img: String,
    val name: String,
    val username: String
) : Parcelable