package com.picpay.desafio.android.user.di

import com.picpay.desafio.android.user.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.user.domain.usecase.GetUsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface DomainModule {
    @Binds
    fun bindsGetUsersUseCase(implementation: GetUsersUseCaseImpl):
            GetUsersUseCase
}
