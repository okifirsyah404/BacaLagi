package com.reader.bacalagi.domain.repository.privacy_policy

import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import com.reader.bacalagi.data.source.PolicyDataSource
import com.reader.bacalagi.utilities.base.ApiResponse
import kotlinx.coroutines.flow.Flow

class PrivacyPolicyRepositoryImpl(private val dataSource: PolicyDataSource) :
    PrivacyPolicyRepository {
    override suspend fun getAllPolicy(): Flow<ApiResponse<List<PrivacyPolicyResponse>>> {
        return dataSource.getAllPolicy()
    }

}