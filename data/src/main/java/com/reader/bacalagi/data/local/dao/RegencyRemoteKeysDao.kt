package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel

@Dao
interface RegencyRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RegencyRemoteKeysModel>)

    @Query("SELECT * FROM regency_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RegencyRemoteKeysModel?

    @Query("DELETE FROM regency_remote_keys")
    suspend fun deleteRemoteKeys()
}