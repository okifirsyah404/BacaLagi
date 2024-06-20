package com.reader.bacalagi.data.network.service

import com.reader.bacalagi.data.base.BasePaginationResponse
import com.reader.bacalagi.data.base.BaseResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("book")
    suspend fun fetchBooks(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): BasePaginationResponse<ProductResponse>

    @GET("book/search/paging")
    suspend fun searchBooks(
        @Query("title") title: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): BasePaginationResponse<ProductResponse>

    @GET("book/search")
    suspend fun searchBookWithoutPaging(
        @Query("q") title: String
    ): BaseResponse<List<ProductResponse>>
}