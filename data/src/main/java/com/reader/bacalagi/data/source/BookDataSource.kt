package com.reader.bacalagi.data.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.mediator.GeneralProductRemoteMediator
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.service.BookService
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

//    fun fetchPagingBooks(): LiveData<PagingData<ProductWithBookModel>> {
//        @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 4,
//            ),
//            remoteMediator = BookRemoteMediator(database, service),
//            pagingSourceFactory = {
//                database.getBookDao().getAllProducts()
//            }
//        ).liveData
//    }
}