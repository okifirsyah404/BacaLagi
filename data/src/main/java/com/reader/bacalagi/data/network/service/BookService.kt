package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("book")
    suspend fun fetchBooks(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): BaseResponse<List<ProductResponse>>
}