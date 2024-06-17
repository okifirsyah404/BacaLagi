package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.request.EditProfileRequest
import com.reader.bacalagi.data.network.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileService {

    @GET("profile")
    suspend fun fetchProfile(): BaseResponse<UserResponse>

    @PUT("profile")
    suspend fun editProfile(
        @Body request: EditProfileRequest
    ): BaseResponse<UserResponse>

    @PUT("/profile/upload")
    @Multipart
//    @Headers("Content-Type: multipart/form-data")
    suspend fun uploadProfilePicture(
        @Part image: MultipartBody.Part
    ): BaseResponse<UserResponse>


}