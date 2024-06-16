package com.reader.bacalagi.data.network.request

data class EditProfileRequest(
    val name: String,
    val phoneNumber: String,
    val regency: String,
    val province: String,
    val address: String
)