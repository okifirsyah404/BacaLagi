package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.response.UserResponse
import retrofit2.http.GET

interface ProfileService {

    @GET("profile")
    suspend fun auth(): BaseResponse<UserResponse>
}