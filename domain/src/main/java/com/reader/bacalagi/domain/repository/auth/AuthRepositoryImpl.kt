package com.reader.bacalagi.domain.repository.auth

import com.reader.bacalagi.data.dto.AuthDto
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val dataSource: AuthDataSource) : AuthRepository {
    override suspend fun auth(firebaseTokenId: String): Flow<ApiResponse<AuthDto>> {
        return dataSource.authorize(firebaseTokenId)
    }
}