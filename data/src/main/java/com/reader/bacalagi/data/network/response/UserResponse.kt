package com.reader.bacalagi.data.network.response

data class UserResponse(
    val id: String,
    val profile: ProfileResponse,
    val account: AccountResponse?
)
