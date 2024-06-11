package com.reader.bacalagi.data.network.response

data class AuthResponse(
    val isRegistered: Boolean,
    val accessToken: String?,
)
