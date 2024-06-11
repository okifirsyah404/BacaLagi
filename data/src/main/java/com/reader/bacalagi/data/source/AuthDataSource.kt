package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.dto.AuthDto
import com.reader.bacalagi.data.local.preference.StoragePreference
import com.reader.bacalagi.data.network.request.AuthRequest
import com.reader.bacalagi.data.network.service.AuthService
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthDataSource(private val service: AuthService, private val pref: StoragePreference) {

    suspend fun authorize(firebaseTokenID: String): Flow<ApiResponse<AuthDto>> {
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

                emit(ApiResponse.Success(AuthDto.fromResponse(response.data)))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: ""))
            }
        }
    }

}