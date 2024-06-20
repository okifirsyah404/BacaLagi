package com.reader.bacalagi.data.source

import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.network.response.ListQuestion
import com.reader.bacalagi.data.network.service.FaqService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class FaqDataSource(private val service: FaqService) {
    suspend fun getAllFaq(): Flow<ApiResponse<FaqResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.getAllFaq()
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }
}
