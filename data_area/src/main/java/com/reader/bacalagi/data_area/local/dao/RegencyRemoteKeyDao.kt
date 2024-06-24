package com.reader.bacalagi.data_area.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.RegencyRemoteKeyModel

@Dao
interface RegencyRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RegencyRemoteKeyModel>)

    @Query("SELECT * FROM regency_remote_key WHERE id = :id")
    suspend fun getRemoteKeyId(id: String): RegencyRemoteKeyModel?

    @Query("DELETE FROM regency_remote_key")
    suspend fun deleteRemoteKeys()
}