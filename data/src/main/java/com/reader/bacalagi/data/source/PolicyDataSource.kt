package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.data.network.service.PolicyService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class PolicyDataSource(private val service: PolicyService) {
    suspend fun getAllPolicy(): Flow<ApiResponse<List<PrivacyPolicyResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getAllPolicy()

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
}