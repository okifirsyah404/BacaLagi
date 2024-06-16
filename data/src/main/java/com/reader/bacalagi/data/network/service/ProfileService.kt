package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.request.EditProfileRequest
import com.reader.bacalagi.data.network.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileService {

    @GET("profile")
    suspend fun auth(): BaseResponse<UserResponse>

    @PUT("profile")
    suspend fun edit(
        @Body request: EditProfileRequest
    ): BaseResponse<UserResponse>

}