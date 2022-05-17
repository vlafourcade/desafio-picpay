package com.picpay.desafio.android.user.domain.usecase

import com.picpay.desafio.android.core.processing.model.Resource
import com.picpay.desafio.android.user.domain.mapper.toModel
import com.picpay.desafio.android.user.domain.model.User
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetUsersUseCase {
    suspend operator fun invoke(
        forceUpdate: Boolean = false
    ): Flow<Resource<out List<User>?>>
}

internal class GetUsersUseCaseImpl @Inject constructor(
    private val repository: UsersRepository
) : GetUsersUseCase {
    // 5 minutes of cache
    private val CACHE_TIME = 5 * 60 * 1000

    override suspend fun invoke(forceUpdate: Boolean) = flow {
        try {
            emit(Resource.Loading())

            val shouldRefresh = shouldRefreshData(forceUpdate)

            val repositoryResult =
                repository.getContacts(
                    forceUpdate = shouldRefresh
                )

            val result = repositoryResult?.map { it.toModel() }

            emit(Resource.Success(result))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable, null))
        }
    }

    private suspend fun shouldRefreshData(forceUpdate: Boolean) =
        forceUpdate || (repository.getLatestRefreshTime() + CACHE_TIME) < System.currentTimeMillis()
}