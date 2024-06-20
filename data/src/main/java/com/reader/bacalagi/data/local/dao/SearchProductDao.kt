package com.reader.bacalagi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.SearchProductModel

@Dao
interface SearchProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<SearchProductModel>)


    @Query("SELECT * FROM search_product")
    fun getAllProducts(): PagingSource<Int, SearchProductModel>

    @Query("DELETE FROM search_product")
    suspend fun deleteAll()

}