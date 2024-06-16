package com.reader.bacalagi.data.network.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    val id: String,
    val name: String,
    @SerializedName("avatarUrl")
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
)
