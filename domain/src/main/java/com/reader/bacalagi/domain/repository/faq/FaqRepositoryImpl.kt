package com.reader.bacalagi.domain.repository.faq
import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.network.response.ListQuestion
import com.reader.bacalagi.data.network.response.UserResponse
import com.reader.bacalagi.data.source.FaqDataSource
import com.reader.bacalagi.data.source.ProfileDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class FaqRepositoryImpl(private val dataSource: FaqDataSource) : FaqRepository {
    override suspend fun getAll(): Flow<ApiResponse<FaqResponse>> {
        return dataSource.getAllFaq()
    }

}
