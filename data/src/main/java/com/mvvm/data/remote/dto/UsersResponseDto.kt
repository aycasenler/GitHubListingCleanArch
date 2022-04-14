package com.mvvm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsersResponseDto(
    @SerializedName("total_count") var totalCount: Long,
    @SerializedName("items") var users: List<UserDto>
)
