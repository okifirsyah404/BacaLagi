package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import retrofit2.http.GET

interface PolicyService {
    @GET("privacy-policy")
    suspend fun getAllPolicy(): BaseResponse<List<PrivacyPolicyResponse>>
}