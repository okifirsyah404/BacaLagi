package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.dto.UserDto
import com.reader.bacalagi.data.network.service.ProfileService
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProfileDataSource(private val service: ProfileService) {

    suspend fun fetchProfile(): Flow<ApiResponse<UserDto>> {

        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.auth()

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                val dto = UserDto.fromResponse(response.data)

                if (dto == null) {
                    emit(ApiResponse.Error("Failed to parse response"))
                    return@flow
                }

                emit(ApiResponse.Success(dto))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: ""))
            }
        }
    }

}