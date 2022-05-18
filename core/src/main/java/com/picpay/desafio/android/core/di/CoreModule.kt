package com.picpay.desafio.android.core.di

import com.picpay.desafio.android.core.di.factory.ViewModelFactoryModule
import com.picpay.desafio.android.core.networking.di.NetworkingModule
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
        ViewModelFactoryModule::class,
        NetworkingModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class CoreModule {
    @Provides
    @Named("IO")
    fun provideCoroutinesDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}