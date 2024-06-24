package com.reader.bacalagi.data_area.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.DistrictRemoteKeyModel

@Dao
interface DistrictRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<DistrictRemoteKeyModel>)

    @Query("SELECT * FROM district_remote_key WHERE id = :id")
    suspend fun getRemoteKeyId(id: String): DistrictRemoteKeyModel?

    @Query("DELETE FROM district_remote_key")
    suspend fun deleteRemoteKeys()
}