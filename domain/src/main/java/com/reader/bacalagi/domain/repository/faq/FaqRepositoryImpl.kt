package com.reader.bacalagi.domain.repository.faq

import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.source.FaqDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FaqRepositoryImpl(private val dataSource: FaqDataSource) : FaqRepository {
    override suspend fun getAll(): Flow<ApiResponse<FaqResponse>> {
        return dataSource.getAllFaq().flowOn(Dispatchers.IO)
    }

}
