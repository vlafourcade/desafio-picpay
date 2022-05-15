package com.picpay.desafio.android.user.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.user.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}