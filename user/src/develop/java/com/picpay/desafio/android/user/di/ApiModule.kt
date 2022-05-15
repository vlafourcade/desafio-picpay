package com.picpay.desafio.android.user.di

import com.picpay.desafio.android.user.data.remote.UsersApi
import com.picpay.desafio.android.user.data.remote.UsersMockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal class ApiModule {
    @Provides
    fun provideApi(): UsersApi = UsersMockApi()
}