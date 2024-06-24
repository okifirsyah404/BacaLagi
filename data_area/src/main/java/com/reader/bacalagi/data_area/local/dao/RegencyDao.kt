package com.reader.bacalagi.data_area.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.RegencyModel

@Dao
interface RegencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(regency: List<RegencyModel>)

    @Query("SELECT * FROM regency")
    fun getAllRegency(): PagingSource<Int, RegencyModel>

    @Query("DELETE FROM regency")
    suspend fun deleteAll()

    @Query("SELECT * FROM regency WHERE code = :code")
    fun getRegencyByCode(code: String): RegencyModel
}