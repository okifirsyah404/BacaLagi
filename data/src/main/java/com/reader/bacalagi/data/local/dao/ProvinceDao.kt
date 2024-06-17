package com.reader.bacalagi.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.ProvinceModel

@Dao
interface ProvinceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(province: List<ProvinceModel>)

    @Query("SELECT * FROM province")
    fun getAllProvinces(): PagingSource<Int, ProvinceModel>

    @Query("DELETE FROM province")
    suspend fun deleteAll()

    @Query("SELECT * FROM province WHERE code = :code")
    fun getProvinceByCode(code: String): ProvinceModel
}