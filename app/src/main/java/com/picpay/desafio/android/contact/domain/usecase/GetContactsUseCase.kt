package com.picpay.desafio.android.contact.domain.usecase

import com.picpay.desafio.android.contact.domain.mapper.toModel
import com.picpay.desafio.android.contact.domain.model.Contact
import com.picpay.desafio.android.contact.domain.repository.ContactsRepository
import com.picpay.desafio.android.core.processing.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface GetContactsUseCase {
    suspend operator fun invoke(forceUpdate: Boolean = false) : Flow<Resource<out List<Contact>?>>
}

internal class GetContactsUseCaseImpl constructor(
    private val repository: ContactsRepository
) : GetContactsUseCase {
    override suspend fun invoke(forceUpdate: Boolean) = flow {
        try{
            emit(Resource.Loading())

            val repositoryResult = repository.getContacts(forceUpdate)

            val result = repositoryResult?.map { it.toModel() }

            emit(Resource.Success(result))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable, null))
        }
    }
}