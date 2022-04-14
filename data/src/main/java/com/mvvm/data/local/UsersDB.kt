package com.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mvvm.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UsersDB : RoomDatabase() {
    abstract val dao: UsersDao
}