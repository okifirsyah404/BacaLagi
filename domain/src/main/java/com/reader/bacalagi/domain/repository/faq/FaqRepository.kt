package com.reader.bacalagi.domain.repository.faq

import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow


interface FaqRepository {
    suspend fun getAll(): Flow<ApiResponse<List<FaqResponse>>>
}