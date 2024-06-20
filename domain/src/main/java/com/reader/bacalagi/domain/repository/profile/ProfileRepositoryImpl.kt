package com.reader.bacalagi.domain.repository.profile

import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.source.ProfileDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class ProfileRepositoryImpl(private val dataSource: ProfileDataSource) : ProfileRepository {
    override suspend fun getProfile(): Flow<ApiResponse<UserResponse>> {
        return dataSource.fetchProfile()
    }

    override suspend fun editProfile(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        image: File?
    ): Flow<ApiResponse<UserResponse>> {
        return dataSource.editProfile(name, phoneNumber, regency, province, address, image).flowOn(
            Dispatchers.IO
        )
    }

}