package com.mvvm.data.repository

import com.mvvm.data.local.entity.UserEntity
import com.mvvm.data.remote.dto.UserDetailDto
import com.mvvm.data.remote.dto.UsersResponseDto
import retrofit2.Response

interface UserRepository {

    //Remote
    suspend fun getUsers(query: String?): Response<UsersResponseDto>

    suspend fun getUserDetail(username: String): Response<UserDetailDto>

    //Local
    suspend fun insertUsers(users: List<UserEntity>)

    suspend fun insertUserDetail(user: UserEntity)

    suspend fun getUserDB(username: String): UserEntity

    suspend fun getUsersDB(): List<UserEntity>

}