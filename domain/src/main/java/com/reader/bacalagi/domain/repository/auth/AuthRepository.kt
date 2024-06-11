package com.reader.bacalagi.domain.repository.auth

import com.reader.bacalagi.data.dto.AuthDto
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun auth(
        firebaseTokenId: String,
    ): Flow<ApiResponse<AuthDto>>

}