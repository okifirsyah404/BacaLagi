package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.request.AuthRegisterRequest
import com.reader.bacalagi.data.network.request.AuthRequest
import com.reader.bacalagi.data.network.response.AuthRegisterResponse
import com.reader.bacalagi.data.network.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth")
    suspend fun auth(
        @Body request: AuthRequest
    ): BaseResponse<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: AuthRegisterRequest
    ): BaseResponse<AuthRegisterResponse>

}