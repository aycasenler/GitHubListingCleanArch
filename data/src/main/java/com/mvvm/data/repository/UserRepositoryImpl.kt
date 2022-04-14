package com.mvvm.data.repository

import com.mvvm.data.local.UsersDao
import com.mvvm.data.local.entity.UserEntity
import com.mvvm.data.remote.ProjectService
import com.mvvm.data.remote.dto.UsersResponseDto
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ProjectService,
    private val dao: UsersDao
) : UserRepository {

    //Remote
    override suspend fun getUsers(query: String?): Response<UsersResponseDto> = api.getUsers(query)

    override suspend fun getUserDetail(username: String) = api.getUserDetail(username)

    //Local
    override suspend fun insertUsers(users: List<UserEntity>) = dao.insertUsers(users)

    override suspend fun insertUserDetail(user: UserEntity) = dao.insertUserDetail(user)

    override suspend fun getUserDB(username: String) = dao.getUser(username)

    override suspend fun getUsersDB() = dao.getUsers()

}