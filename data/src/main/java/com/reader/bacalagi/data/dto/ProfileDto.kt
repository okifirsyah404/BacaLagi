package com.reader.bacalagi.data.dto

import com.reader.bacalagi.data.network.response.ProfileResponse

data class ProfileDto(
    val id: String,
    val name: String,
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
) {
    companion object {
        fun fromResponse(response: ProfileResponse?) = response?.let {
            ProfileDto(
                id = it.id,
                name = response.name,
                avatarURL = response.avatarURL,
                phoneNumber = response.phoneNumber,
                adminAreaLocality = response.adminAreaLocality,
                cityLocality = response.cityLocality,
                address = response.address
            )
        }
    }
}
