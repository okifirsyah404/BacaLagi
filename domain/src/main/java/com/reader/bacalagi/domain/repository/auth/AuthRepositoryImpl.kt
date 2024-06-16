package com.reader.bacalagi.domain.repository.auth

import com.reader.bacalagi.data.network.response.AuthResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun auth(firebaseTokenId: String): Flow<ApiResponse<AuthResponse>> {
        return dataSource.authorize(firebaseTokenId)
    }

    override suspend fun register(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        firebaseTokenId: String
    ): Flow<ApiResponse<UserResponse>> {
        return dataSource.register(name, phoneNumber, regency, province, address, firebaseTokenId)
    }


}