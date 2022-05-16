package com.picpay.desafio.android.user.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DataModule::class,
        DomainModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class UserModule