package com.mvvm.data.remote.dto

import com.mvvm.data.local.entity.UserEntity
import com.google.gson.annotations.SerializedName

data class UserDetailDto(
    @SerializedName("id") val id: Long,
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("public_repos") val publicRepos: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
) {
    fun toUserEntity(isFav: Boolean) = UserEntity(
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
