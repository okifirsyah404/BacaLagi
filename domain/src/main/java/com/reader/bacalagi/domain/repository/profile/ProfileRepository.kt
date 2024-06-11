package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.dto.UserDto
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(): Flow<ApiResponse<UserDto>>
}