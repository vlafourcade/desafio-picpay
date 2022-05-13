package com.picpay.desafio.android.core.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import java.io.Serializable
import java.lang.Exception

interface LocalStorage {
    fun <T: Serializable> put(key: String, data: T)

    fun <T: Serializable> get(key: String, type: Class<T>) : T?

    fun delete(key: String)
}

internal class LocalStorageImpl constructor(
    context: Context,
    storageName: String
) : LocalStorage {
    private val serializer = GsonBuilder().serializeNulls().create()

    private val storage: SharedPreferences = context.getSharedPreferences(storageName, Context.MODE_PRIVATE)

    override fun <T : Serializable> put(key: String, data: T) {
        storage.edit().putString(key, serializer.toJson(data)).apply()
    }

    override fun <T : Serializable> get(key: String, type: Class<T>): T? {
        return try {
            storage.getString(key, null)?.let {
                serializer.fromJson(it, type)
            }
        }catch (ex: Exception) {
            null
        }
    }

    override fun delete(key: String) {
        storage.edit().remove(key).apply()
    }
}