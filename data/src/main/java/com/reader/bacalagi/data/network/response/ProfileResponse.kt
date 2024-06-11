package com.reader.bacalagi.data.network.response

data class ProfileResponse(
    val id: String,
    val name: String,
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
)
