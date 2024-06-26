package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ProductService {
    @GET("book/author/post")
    suspend fun fetchProduct(): BaseResponse<ProductResponse>

    @GET("book/author/post")
    suspend fun fetchBook(): BaseResponse<List<ProductResponse>>

    @GET("book/author/post/{id}/sold-out")
    suspend fun soldOut(@Path("id") id: String): BaseResponse<ProductResponse>

    @DELETE("book/author/post/{id}")
    suspend fun delete(@Path("id") id: String): BaseResponse<ProductResponse>

    @POST("book/author/post")
    @Multipart
    suspend fun postProduct(
        @Part image: MultipartBody.Part,
        @Part("author") author: RequestBody,
        @Part("title") title: RequestBody,
        @Part("publisher") publisher: RequestBody,
        @Part("publishYear") publishYear: RequestBody,
        @Part("buyPrice") buyPrice: RequestBody,
        @Part("finalPrice") finalPrice: RequestBody,
        @Part("ISBN") ISBN: RequestBody,
        @Part("language") language: RequestBody,
        @Part("description") description: RequestBody
    ): BaseResponse<ProductResponse>

    @POST("book/predict")
    @Multipart
    suspend fun predictProduct(
        @Part image: MultipartBody.Part,
        @Part("buyPrice") buyPrice: RequestBody
    ): BaseResponse<PredictionResponse>

    @PUT("/book/author/post/{id}")
    @Multipart
    suspend fun editProduct(
        @Path("id") id: String,
        @Part image: MultipartBody.Part,
        @Part("author") author: RequestBody,
        @Part("title") title: RequestBody,
        @Part("publisher") publisher: RequestBody,
        @Part("publishYear") publishYear: RequestBody,
        @Part("buyPrice") buyPrice: RequestBody,
        @Part("finalPrice") finalPrice: RequestBody,
        @Part("ISBN") ISBN: RequestBody,
        @Part("language") language: RequestBody,
        @Part("description") description: RequestBody
    ): BaseResponse<ProductResponse>
}
