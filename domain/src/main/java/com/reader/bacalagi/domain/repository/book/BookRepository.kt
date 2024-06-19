package com.reader.bacalagi.domain.repository.book

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.reader.bacalagi.data.local.model.BookModel
import com.reader.bacalagi.data.local.model.ProductModel

interface BookRepository {

    fun getPagingBooks(): LiveData<PagingData<ProductModel>>
}