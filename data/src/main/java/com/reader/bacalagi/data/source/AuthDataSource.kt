package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.local.preference.StoragePreference
import com.reader.bacalagi.data.network.request.AuthRegisterRequest
import com.reader.bacalagi.data.network.request.AuthRequest
import com.reader.bacalagi.data.network.response.AuthResponse
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.network.service.AuthService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class AuthDataSource(private val service: AuthService, private val pref: StoragePreference) {

    suspend fun authorize(firebaseTokenID: String): Flow<ApiResponse<AuthResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.auth(AuthRequest(firebaseTokenID))

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                if (response.data.isRegistered) {
                    response.data.accessToken?.let { pref.saveAccessToken(it) }
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    suspend fun register(
        name: String,
        phoneNumber: String,
        regency: String,
        province: String,
        address: String,
        firebaseTokenID: String
    ): Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.register(
                    AuthRegisterRequest(
                        name = name,
                        phoneNumber = phoneNumber,
                        regency = regency,
                        province = province,
                        address = address,
                        firebaseTokenId = firebaseTokenID
                    )
                )

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                response.data.accessToken.let { pref.saveAccessToken(it) }


                emit(ApiResponse.Success(response.data.user))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }
}