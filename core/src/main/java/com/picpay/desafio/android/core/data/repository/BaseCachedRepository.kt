package com.picpay.desafio.android.core.data.repository

abstract class BaseCachedRepository<Remote, Local, Dto> {
    suspend fun fetchData(forceUpdate: Boolean): Dto {
        try {
            if(forceUpdate) {
                val data = fetchRemoteData()

                val localEntity = parseToLocalEntity(data)

                saveLocalData(localEntity)

                updateLatestRefreshTime()
            }

            val result = fetchLocalData()

            return parseToDto(result)

        } catch (throwable: Throwable) {
            // TODO: Parse error
            throw throwable
        }
    }

    abstract suspend fun fetchRemoteData(): Remote

    abstract suspend fun fetchLocalData(): Local

    abstract suspend fun saveLocalData(localData: Local?)

    abstract fun parseToLocalEntity(remoteData: Remote): Local

    abstract fun parseToDto(localData: Local): Dto

    abstract suspend fun updateLatestRefreshTime()

    abstract fun getLatestRefreshTime(): Long
}