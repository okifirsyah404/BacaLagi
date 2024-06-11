package com.reader.bacalagi.data.dto

import com.reader.bacalagi.data.network.response.UserResponse

data class UserDto(
    val id: String,
    val profile: ProfileDto?
) {
    companion object {
        fun fromResponse(response: UserResponse?) = response?.let {
            UserDto(
                id = it.id,
                profile = ProfileDto.fromResponse(response.profile)
            )
        }
    }
}
