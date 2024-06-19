package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.network.response.PrivacyPolicyResponse
import retrofit2.http.GET

interface PrivacyPolicyService {
    @GET("privacy-policy")
    suspend fun getAllPolicy(): PrivacyPolicyResponse
}