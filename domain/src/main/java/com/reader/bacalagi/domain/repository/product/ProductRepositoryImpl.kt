package com.reader.bacalagi.domain.repository.product

import com.reader.bacalagi.data.local.model.ProductDataSource
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class ProductRepositoryImpl(private val dataSource: ProductDataSource) : ProductRepository {

    override suspend fun getProduct(): Flow<ApiResponse<ProductResponse>> {
        return dataSource.fetchProduct()
    }

    override suspend fun getMyBooks(): Flow<ApiResponse<List<ProductResponse>>> {
        return dataSource.fetchMyBook()
    }

    override suspend fun predictProduct(
        buyPrice: String,
        image: File
    ): Flow<ApiResponse<PredictionResponse>> =
        dataSource.predictProduct(
            image = image,
            buyPrice = buyPrice
        ).flowOn(Dispatchers.IO)

    override suspend fun postProduct(
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
    ): Flow<ApiResponse<ProductResponse>> =
        dataSource.postProduct(
            title,
            author,
            publisher,
            publishYear,
            buyPrice,
            finalPrice,
            ISBN,
            language,
            description,
            image
        ).flowOn(Dispatchers.IO)


    override suspend fun editProduct(
        title: String,
        author: String,
        publisher: String,
        publishYear: Long,
        buyPrice: Long,
        finalPrice: Long,
        ISBN: String,
        language: String,
        description: String,
        image: File?
    ): Flow<ApiResponse<ProductResponse>> {
        return dataSource.editProduct(
            title,
            author,
            publisher,
            publishYear,
            buyPrice,
            finalPrice,
            ISBN,
            language,
            description,
            image
        )
    }
}