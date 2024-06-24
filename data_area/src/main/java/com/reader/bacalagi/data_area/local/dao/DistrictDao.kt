package com.reader.bacalagi.data_area.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.DistrictModel

@Dao
interface DistrictDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(regency: List<DistrictModel>)

    @Query("SELECT * FROM district")
    fun getAllRegency(): PagingSource<Int, DistrictModel>

    @Query("DELETE FROM district")
    suspend fun deleteAll()

    @Query("SELECT * FROM district WHERE code = :code")
    fun getDistrictByCode(code: String): DistrictModel
}