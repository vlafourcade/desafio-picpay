package com.picpay.desafio.android.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module(
    includes = [
        DataModule::class,
        DomainModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class UserModule