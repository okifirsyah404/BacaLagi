package com.reader.bacalagi.data.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.data.local.model.SearchProductModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.mediator.GeneralProductRemoteMediator
import com.reader.bacalagi.data.mediator.SearchProductRemoteMediator
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.service.BookService
import com.reader.bacalagi.data.utils.ApiResponse
import com.reader.bacalagi.data.utils.extension.createErrorResponse
import com.reader.bacalagi.data.utils.extension.getHttpBodyErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber

class BookDataSource(private val service: BookService, private val database: BacaLagiDatabase) {

    fun fetchProducts(): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchBooks()

                if (response.data.isEmpty()) {
                    emit(ApiResponse.Error(response.message))
                    return@flow
                }

                database.getGeneralProductDao()
                    .insertAllProducts(response.data.map {
                        GeneralProductModel.fromResponse(it)
                    })

                emit(ApiResponse.Success(response.data))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message ?: ""))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    fun searchProducts(title: String): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.searchBookWithoutPaging(title)

                if (response.data?.isEmpty() == true) {
                    emit(ApiResponse.Error("Data not found"))
                    return@flow
                }

                Timber.d("searchProducts: $response")

                emit(ApiResponse.Success(response.data!!))
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.getHttpBodyErrorMessage()))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createErrorResponse()))
            }
        }
    }

    fun fetchProductDetail(id: String): Flow<ApiResponse<ProductResponse>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.fetchBookDetail(id)

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

    @OptIn(ExperimentalPagingApi::class)
    fun fetchPagingProducts(): LiveData<PagingData<GeneralProductModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
            ),
            remoteMediator = GeneralProductRemoteMediator(database, service),
            pagingSourceFactory = {
                database.getGeneralProductDao().getAllProducts()
            }
        ).liveData
    }

    suspend fun deletePagingSearchProduct() {
        database.getSearchProductDao().deleteAll()
    }

    @OptIn(ExperimentalPagingApi::class)
    fun fetchPagingSearchProduct(searchQuery: String): LiveData<PagingData<SearchProductModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 4,
            ),
            remoteMediator = SearchProductRemoteMediator(database, service, searchQuery),
            pagingSourceFactory = {
                database.getSearchProductDao().getAllProducts()
            }
        ).liveData
    }


}