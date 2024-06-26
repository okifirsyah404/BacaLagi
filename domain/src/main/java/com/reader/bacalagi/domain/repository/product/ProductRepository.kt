package com.reader.bacalagi.domain.repository.product

import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface ProductRepository {
    suspend fun getProduct(): Flow<ApiResponse<ProductResponse>>

    suspend fun getMyBooks(): Flow<ApiResponse<List<ProductResponse>>>

    suspend fun soldOut(
        id: String
    ): Flow<ApiResponse<ProductResponse>>

    suspend fun delete(
        id: String
    ): Flow<ApiResponse<ProductResponse>>

    suspend fun editProduct(
        id: String,
        title: String,
        author: String,
        publisher: String,
        publishYear: String,
        buyPrice: String,
        finalPrice: String,
        ISBN: String,
        language: String,
        description: String,
        image: File
    ): Flow<ApiResponse<ProductResponse>>

    suspend fun predictProduct(
        buyPrice: String,
        image: File
    ): Flow<ApiResponse<PredictionResponse>>

    suspend fun postProduct(
        title: String,
        author: String,
        publisher: String,
        publishYear: String,
        buyPrice: String,
        finalPrice: String,
        ISBN: String,
        language: String,
        description: String,
        image: File
    ): Flow<ApiResponse<ProductResponse>>

//    suspend fun deleteProduct(
//        id: String
//    ): Flow<ApiResponse<ProductResponse>>
}