package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResponse<UserResponse>>
    suspend fun editProfile(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        image: File?
    ): Flow<ApiResponse<UserResponse>>
}