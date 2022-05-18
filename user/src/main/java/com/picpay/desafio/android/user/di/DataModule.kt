package com.picpay.desafio.android.user.di

import android.app.Application
import androidx.room.Room
import com.picpay.desafio.android.user.data.local.UserDatabase
import com.picpay.desafio.android.user.data.repository.UsersRepositoryImpl
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module(
    includes = [
        AbstractDataModule::class,
        ApiModule::class
    ]
)
@InstallIn(ViewModelComponent::class)
internal class DataModule {
    @Provides
    fun provideDatabase(application: Application): UserDatabase =
        Room.databaseBuilder(application, UserDatabase::class.java, "users_database").build()

    @Provides
    fun provideDao(database: UserDatabase) = database.usersDao()
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface AbstractDataModule {
    @Binds
    fun bindsRepository(implementation: UsersRepositoryImpl):
            UsersRepository
}
