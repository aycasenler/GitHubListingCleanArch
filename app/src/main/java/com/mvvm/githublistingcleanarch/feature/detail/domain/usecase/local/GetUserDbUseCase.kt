package com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.local

import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class GetUserDbUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun getUser(username: String) = repository.getUserDB(username)
}