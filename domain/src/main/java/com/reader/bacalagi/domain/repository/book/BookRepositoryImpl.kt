package com.reader.bacalagi.domain.repository.book

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.source.BookDataSource
import com.reader.bacalagi.data.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class BookRepositoryImpl(private val dataSource: BookDataSource) : BookRepository {

    fun getProducts(): Flow<ApiResponse<List<ProductResponse>>> =
        dataSource.fetchProducts().flowOn(Dispatchers.IO)

    fun getPagingProducts(): LiveData<PagingData<GeneralProductModel>> =
        dataSource.fetchPagingProducts()

//    override fun getPagingBooks(): LiveData<PagingData<ProductModel>> =
//        dataSource.fetchPagingBooks()

}