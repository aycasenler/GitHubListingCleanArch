package com.mvvm.githublistingcleanarch.feature.listing.domain.usecase.local

import com.mvvm.data.local.entity.UserEntity
import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class InsertUsersDbUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun insertUsers(users: List<UserEntity>) = repository.insertUsers(users)
}