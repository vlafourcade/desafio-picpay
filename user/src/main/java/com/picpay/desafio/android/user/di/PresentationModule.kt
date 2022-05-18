package com.picpay.desafio.android.user.di

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.core.di.key.ViewModelKey
import com.picpay.desafio.android.user.presentation.adapter.UserAdapter
import com.picpay.desafio.android.user.presentation.adapter.UserAdapterImpl
import com.picpay.desafio.android.user.presentation.viewmodel.ListUsersViewModel
import com.picpay.desafio.android.user.presentation.viewmodel.ListUsersViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(FragmentComponent::class)
internal interface PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListUsersViewModel::class)
    fun bindListUsersViewModel(viewModel: ListUsersViewModelImpl): ViewModel

    @Binds
    fun bindUserAdapter(adapter: UserAdapterImpl): UserAdapter
}
