package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.request.EditProductRequest
import com.reader.bacalagi.data.network.request.PostProductRequest
import com.reader.bacalagi.data.network.request.PredictProductRequest
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProductService {
    @GET("book/author/post")
    suspend fun fetchProduct(): BaseResponse<ProductResponse>

    @POST("book/author/post")
    @Multipart
    suspend fun postProduct(
        @Body request: PostProductRequest
    ): BaseResponse<ProductResponse>

    @POST("book/predict")
    @Multipart
    suspend fun predictProduct(
        @Part image: MultipartBody.Part,
        @Part("buyPrice") buyPrice: RequestBody
    ): BaseResponse<PredictionResponse>

    @PUT("/book/author/post")
    @Multipart
    suspend fun editProduct(
        @Body request: EditProductRequest
    ): BaseResponse<ProductResponse>
}
