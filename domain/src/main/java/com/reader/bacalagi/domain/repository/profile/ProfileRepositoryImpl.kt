package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.source.ProfileDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResponse<UserResponse>> {
        return dataSource.fetchProfile()
    }

}