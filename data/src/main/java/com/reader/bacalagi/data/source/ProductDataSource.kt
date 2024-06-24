package com.reader.bacalagi.data.local.model

import com.reader.bacalagi.data.network.response.PredictionResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.service.ProductService
import com.reader.bacalagi.utilities.base.ApiResponse
import com.reader.bacalagi.utilities.extension.createErrorResponse
import com.reader.bacalagi.utilities.extension.getHttpBodyErrorMessage
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

    suspend fun fetchMyBook(): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchBook()
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

    suspend fun soldOut(
        id: String
    ): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.soldOut(
                    id = id
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

    suspend fun delete(
        id: String
    ): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.delete(
                    id = id
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
        publishYear: String,
        buyPrice: String,
        finalPrice: String,
        ISBN: String,
        language: String,
        description: String,
        image: File
    ): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.postProduct(
                    title = title.toRequestBody(),
                    author = author.toRequestBody(),
                    publisher = publisher.toRequestBody(),
                    publishYear = publishYear.toRequestBody(),
                    buyPrice = buyPrice.toRequestBody(),
                    finalPrice = finalPrice.toRequestBody(),
                    ISBN = ISBN.toRequestBody(),
                    language = language.toRequestBody(),
                    description = description.toRequestBody(),
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
    ): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.editProduct(
                    id = id,
                    title = title.toRequestBody(),
                    author = author.toRequestBody(),
                    publisher = publisher.toRequestBody(),
                    publishYear = publishYear.toRequestBody(),
                    buyPrice = buyPrice.toRequestBody(),
                    finalPrice = finalPrice.toRequestBody(),
                    ISBN = ISBN.toRequestBody(),
                    language = language.toRequestBody(),
                    description = description.toRequestBody(),
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

    private fun File.toMultipart(): MultipartBody.Part {
        val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", this.name, requestFile)
    }
}
