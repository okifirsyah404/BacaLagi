package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.dto.UserDto
import com.reader.bacalagi.data.source.AuthDataSource
import com.reader.bacalagi.data.source.ProfileDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResponse<UserDto>> {
        return dataSource.fetchProfile()
    }
    
}