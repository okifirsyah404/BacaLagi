package com.reader.bacalagi.data.local.model

import com.reader.bacalagi.data.network.request.EditProductRequest
import com.reader.bacalagi.data.network.request.PostProductRequest
import com.reader.bacalagi.data.network.request.PredictProductRequest
import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.service.ProductService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class ProductDataSource(private val service: ProductService) {

    suspend fun fetchProduct(): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchProduct()
                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                } else {
                    emit(ApiResponse.Success(response.data))
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    suspend fun predictProduct(
        buyPrice: String,
        image: File
    ): Flow<ApiResponse<PredictionResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.predictProduct(
                        buyPrice = buyPrice.toRequestBody(),
                        image = image.toMultipart()
                )

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    suspend fun postProduct(
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
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.postProduct(
                    PostProductRequest(
                        title = title,
                        author = author,
                        publisher = publisher,
                        publishYear = publishYear,
                        buyPrice = buyPrice,
                        finalPrice = finalPrice,
                        ISBN = ISBN,
                        language = language,
                        description = description,
                        image = image
                    )
                )

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    suspend fun editProduct(
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
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.editProduct(
                    EditProductRequest(
                        title = title,
                        author = author,
                        publisher = publisher,
                        publishYear = publishYear,
                        buyPrice = buyPrice,
                        finalPrice = finalPrice,
                        ISBN = ISBN,
                        language = language,
                        description = description,
                        image = image
                    )
                )

                if (response.data == null) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                emit(ApiResponse.Success(response.data))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    private fun File.toMultipart(): MultipartBody.Part {
        val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", this.name, requestFile)
    }
}
