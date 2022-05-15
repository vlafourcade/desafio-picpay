package com.picpay.desafio.android.user.di

import com.picpay.desafio.android.user.data.remote.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal class ApiModule {
    @Provides
    fun provideApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}
