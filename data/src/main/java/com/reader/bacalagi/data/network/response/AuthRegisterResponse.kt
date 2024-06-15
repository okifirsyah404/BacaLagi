package com.reader.bacalagi.data.network.response

data class AuthRegisterResponse(
    val user: UserResponse,
    val accessToken: String
)
