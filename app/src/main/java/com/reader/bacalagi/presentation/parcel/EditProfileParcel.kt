package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.data.network.response.ProfileResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditProfileParcel(
    val name: String,
    val phoneNumber: String,
    val avatarUrl: String?,
    val regency: String,
    val province: String,
    val address: String?
) : Parcelable {
    companion object {
        fun fromResponse(response: ProfileResponse): EditProfileParcel {
            return EditProfileParcel(
                name = response.name,
                phoneNumber = response.phoneNumber,
                avatarUrl = response.avatarURL,
                regency = response.cityLocality,
                province = response.adminAreaLocality,
                address = response.address
            )
        }
    }
}