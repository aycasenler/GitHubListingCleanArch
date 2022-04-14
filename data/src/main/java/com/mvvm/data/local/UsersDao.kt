package com.mvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm.data.local.entity.UserEntity

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(user: UserEntity)

    @Query("SELECT * FROM userEntity WHERE username == :username")
    suspend fun getUser(username: String): UserEntity

    @Query("SELECT * FROM userEntity")
    suspend fun getUsers(): List<UserEntity>

}