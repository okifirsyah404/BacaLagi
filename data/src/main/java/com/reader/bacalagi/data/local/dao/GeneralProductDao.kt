package com.reader.bacalagi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.GeneralProductModel

@Dao
interface GeneralProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<GeneralProductModel>)


    @Query("SELECT * FROM general_product")
    fun getAllProducts(): PagingSource<Int, GeneralProductModel>

    @Query("DELETE FROM general_product")
    suspend fun deleteAll()

}