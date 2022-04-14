package com.mvvm.githublistingcleanarch.feature.detail.domain.usecase.local

import com.mvvm.data.local.entity.UserEntity
import com.mvvm.data.repository.UserRepository
import javax.inject.Inject

class UpdateUserDbUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend fun updateUser(user: UserEntity) = repository.insertUserDetail(user)
}