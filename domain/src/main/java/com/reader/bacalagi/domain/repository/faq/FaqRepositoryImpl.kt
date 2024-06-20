package com.reader.bacalagi.domain.repository.faq

import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.source.FaqDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow


class FaqRepositoryImpl(private val dataSource: FaqDataSource) : FaqRepository {
    override suspend fun getAll(): Flow<ApiResponse<List<FaqResponse>>> {
        return dataSource.getAllFaq()
    }

}
