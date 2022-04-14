package com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.remote

import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun getUser(username: String) = repository.getUserDetail(username)
}