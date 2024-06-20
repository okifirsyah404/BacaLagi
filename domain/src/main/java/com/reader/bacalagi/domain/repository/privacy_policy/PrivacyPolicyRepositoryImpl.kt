package com.reader.bacalagi.domain.repository.privacy_policy

import com.reader.bacalagi.data.network.response.FaqResponse
import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.data.source.FaqDataSource
import com.reader.bacalagi.data.source.PolicyDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.domain.repository.faq.FaqRepository
import kotlinx.coroutines.flow.Flow

class PrivacyPolicyRepositoryImpl(private val dataSource: PolicyDataSource) : PrivacyPolicyRepository {
    override suspend fun getAllPolicy(): Flow<ApiResponse<PrivacyPolicyResponse>> {
        return dataSource.getAllPolicy()
    }

}