package com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.remote

import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun getUsers(query: String?) = repository.getUsers(query)
}