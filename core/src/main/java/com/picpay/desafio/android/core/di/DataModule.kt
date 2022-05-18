package com.picpay.desafio.android.core.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.core.data.local.LocalStorage
import com.picpay.desafio.android.core.data.local.LocalStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
internal class DataModule {
    @Provides
    fun provideDefaultSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("desafio-picpay", Context.MODE_PRIVATE)

    @Provides
    fun provideDefaultJsonSerializer(): Gson = GsonBuilder().serializeNulls().create()

    @Provides
    fun provideLocalStorage(storage: SharedPreferences, serializer: Gson): LocalStorage =
        LocalStorageImpl(storage, serializer)
}