package com.reader.bacalagi.data_area.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.ProvinceRemoteKeyModel

@Dao
interface ProvinceRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ProvinceRemoteKeyModel>)

    @Query("SELECT * FROM province_remote_key WHERE id = :id")
    suspend fun getRemoteKeyId(id: String): ProvinceRemoteKeyModel?

    @Query("DELETE FROM province_remote_key")
    suspend fun deleteRemoteKeys()
}