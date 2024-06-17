package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.network.request.EditProfileRequest
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.network.service.ProfileService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import com.reader.bacalagi.data.utils.extension.toMultipart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File


class ProfileDataSource(private val service: ProfileService) {

    suspend fun fetchProfile(): Flow<ApiResponse<UserResponse>> {

        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchProfile()

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    suspend fun editProfile(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        file: File?
    ): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.editProfile(
                    EditProfileRequest(
                        name = name,
                        phoneNumber = phoneNumber,
                        regency = regency,
                        province = province,
                        address = address
                    )
                )

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                if (file != null) {
                    val uploadResponse = service.uploadProfilePicture(file.toMultipart())
                    
                    if (uploadResponse.data == null) {
                        emit(ApiResponse.Error(uploadResponse.message))
                        return@flow
                    }
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

}