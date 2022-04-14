package com.mvvm.data.remote

import com.mvvm.data.remote.dto.UserDetailDto
import com.mvvm.data.remote.dto.UsersResponseDto
import retrofit2.Response
import retrofit2.http.*

interface ProjectService {

    object Endpoint {
        const val search = "search/users"
        const val detail = "users/{login}"
    }

    @GET(Endpoint.search)
    suspend fun getUsers(
        @Query("q") query: String?,
    ): Response<UsersResponseDto>

    @GET(Endpoint.detail)
    suspend fun getUserDetail(
        @Path("login") username: String
    ): Response<UserDetailDto>
}
