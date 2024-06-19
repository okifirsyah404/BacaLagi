package com.reader.bacalagi.domain.repository.book

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.BookModel
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.data.source.BookDataSource

class BookRepositoryImpl(private val dataSource: BookDataSource) : BookRepository {
    override fun getPagingBooks(): LiveData<PagingData<ProductModel>> =
        dataSource.fetchPagingBooks()

}