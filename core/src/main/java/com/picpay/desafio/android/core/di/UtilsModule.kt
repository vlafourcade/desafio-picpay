package com.picpay.desafio.android.core.di

import com.picpay.desafio.android.core.utils.image.ImageLoader
import com.picpay.desafio.android.core.utils.image.ImageLoaderImpl
import com.picpay.desafio.android.core.utils.logging.Logger
import com.picpay.desafio.android.core.utils.logging.LoggerImpl
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Provides
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    fun provideImageLoader(picasso: Picasso): ImageLoader = ImageLoaderImpl(picasso)

    @Provides
    fun provideLogger(): Logger = LoggerImpl()
}