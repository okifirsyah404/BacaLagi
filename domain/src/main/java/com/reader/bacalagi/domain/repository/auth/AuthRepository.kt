package com.reader.bacalagi.domain.repository.auth

import com.reader.bacalagi.data.network.response.AuthResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun auth(
        firebaseTokenId: String,
    ): Flow<ApiResponse<AuthResponse>>

    suspend fun register(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        firebaseTokenId: String,
    ): Flow<ApiResponse<UserResponse>>

}