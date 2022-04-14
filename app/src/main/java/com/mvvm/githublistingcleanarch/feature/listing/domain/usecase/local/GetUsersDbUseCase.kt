package com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.local

import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class GetUsersDbUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun getUsers() = repository.getUsersDB()
}