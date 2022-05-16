package com.picpay.desafio.android.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.core.processing.model.Resource
import com.picpay.desafio.android.core.utils.livedata.Event
import com.picpay.desafio.android.user.domain.model.User
import com.picpay.desafio.android.user.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

internal abstract class ListUsersViewModel : ViewModel() {
    abstract val data: LiveData<Event<List<User>?>>

    abstract val error: LiveData<Event<Throwable?>>

    abstract val isLoading: LiveData<Boolean>

    abstract fun fetchData(forceUpdate: Boolean = false)
}

internal class ListUsersViewModelImpl @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    @Named("IO") private val coroutineDispatcher: CoroutineDispatcher
) : ListUsersViewModel() {
    override val isLoading = MutableLiveData(false)
    override val data = MutableLiveData<Event<List<User>?>>()
    override val error = MutableLiveData<Event<Throwable?>>()

    override fun fetchData(forceUpdate: Boolean) {
        viewModelScope.launch(coroutineDispatcher) {
            getUsersUseCase(forceUpdate).collect {
                when (it) {
                    is Resource.Loading -> isLoading.postValue(true)
                    is Resource.Success -> {
                        isLoading.postValue(false)
                        data.postValue(Event(it.data))
                    }
                    is Resource.Error -> {
                        isLoading.postValue(false)
                        error.postValue(Event(it.error))
                    }
                }
            }
        }
    }
}