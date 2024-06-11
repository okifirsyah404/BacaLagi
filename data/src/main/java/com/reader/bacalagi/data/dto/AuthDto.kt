package com.reader.bacalagi.data.dto

import com.reader.bacalagi.data.network.response.AuthResponse

data class AuthDto(
    val isRegistered: Boolean,
    val accessToken: String?,
) {
    companion object {
        fun fromResponse(response: AuthResponse): AuthDto {
            return AuthDto(
                isRegistered = response.isRegistered,
                accessToken = response.accessToken,
            )
        }
    }
}