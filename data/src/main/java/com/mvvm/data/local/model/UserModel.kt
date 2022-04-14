package com.mvvm.data.local.model

import com.mvvm.data.local.entity.UserEntity
import java.io.Serializable

data class UserModel(
    val id: Long,
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
    var isFav: Boolean = false
):Serializable{

    fun toUserEntity() = UserEntity(
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
        isFav
    )
}
