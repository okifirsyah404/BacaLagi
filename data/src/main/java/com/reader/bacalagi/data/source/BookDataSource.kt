package com.reader.bacalagi.data.source

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.data.local.room.BacaLagiDatabase
import com.reader.bacalagi.data.mediator.BookRemoteMediator
import com.reader.bacalagi.data.network.service.BookService

class BookDataSource(private val service: BookService, private val database: BacaLagiDatabase) {
    fun fetchPagingBooks(): LiveData<PagingData<ProductModel>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 4,
            ),
            remoteMediator = BookRemoteMediator(database, service),
            pagingSourceFactory = {
                database.getBookDao().getAllProducts()
            }
        ).liveData
    }
}