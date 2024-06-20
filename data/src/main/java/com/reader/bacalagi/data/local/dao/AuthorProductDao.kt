package com.reader.bacalagi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.AuthorProductModel

@Dao
interface AuthorProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<AuthorProductModel>)


    @Query("SELECT * FROM author_product")
    fun getAllProducts(): PagingSource<Int, AuthorProductModel>

    @Query("DELETE FROM author_product")
    suspend fun deleteAll()

}