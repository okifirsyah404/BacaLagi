package com.reader.bacalagi.domain.repository.auth

import com.reader.bacalagi.data.network.response.AuthResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.utilities.base.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

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
            .flowOn(
                Dispatchers.IO
            )
    }


}