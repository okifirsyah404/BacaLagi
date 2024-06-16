package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResponse<UserResponse>>
    suspend fun edit(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String
    ): Flow<ApiResponse<UserResponse>>
}