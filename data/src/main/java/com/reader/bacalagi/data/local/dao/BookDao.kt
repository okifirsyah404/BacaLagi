package com.reader.bacalagi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.BookModel
import com.reader.bacalagi.data.local.model.ProductModel

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(product: List<ProductModel>)

    @Query("SELECT * FROM product")
    fun getAllProducts(): PagingSource<Int, ProductModel>

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllBooks(book: List<BookModel>)

    @Query("SELECT * FROM book")
    fun getAllBooks(): PagingSource<Int, BookModel>

    @Query("DELETE FROM book")
    suspend fun deleteAllBooks()
}