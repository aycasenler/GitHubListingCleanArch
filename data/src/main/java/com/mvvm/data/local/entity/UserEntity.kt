package com.mvvm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mvvm.data.local.model.UserModel
import com.mvvm.data.remote.dto.UserDetailDto
import com.mvvm.data.remote.dto.UserDto

@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
    val username: String,
    val avatarUrl: String,
    val name: String? = "",
    val company: String? = "",
    val location: String? = "",
    val email: String? = "",
    val bio: String? = "",
    val publicRepos: Int? = 0,
    val followers: Int? = 0,
    val following: Int? = 0,
    var isFav: Boolean? = false
) {
    fun toUser() = UserDto(
        id,
        username,
        avatarUrl
    )

    fun toUserDetail() = UserDetailDto(
        id,
        username,
        avatarUrl,
        name ?: "",
        company ?: "",
        location ?: "",
        email ?: "",
        bio ?: "",
        publicRepos,
        followers,
        following,
    )

    fun toUserModel() = UserModel(
        id,
        username,
        avatarUrl,
        name ?: "",
        company ?: "",
        location ?: "",
        email ?: "",
        bio ?: "",
        publicRepos,
        followers,
        following,
        isFav ?: false
    )
}
