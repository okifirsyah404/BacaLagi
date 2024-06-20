package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.network.response.FaqResponse
import retrofit2.http.GET

interface FaqService {
    @GET("faq")
    suspend fun getAllFaq(): FaqResponse
}