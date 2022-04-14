package com.mvvm.data.remote.dto

import com.mvvm.data.local.entity.UserEntity
import com.google.gson.annotations.SerializedName
import com.mvvm.data.local.model.UserModel

data class UserDto(
    @SerializedName("id") var id: Long,
    @SerializedName("login") var username: String,
    @SerializedName("avatar_url") var avatarUrl: String
) {
    fun toUserEntity() = UserEntity(
        id,
        username,
        avatarUrl
    )

    fun toUserModel() = UserModel(
        id,
        username,
        avatarUrl
    )
}
