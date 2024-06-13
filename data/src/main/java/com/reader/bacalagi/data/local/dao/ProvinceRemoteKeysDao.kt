package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel

@Dao
interface ProvinceRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ProvinceRemoteKeysModel>)

    @Query("SELECT * FROM province_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): ProvinceRemoteKeysModel?

    @Query("DELETE FROM province_remote_keys")
    suspend fun deleteRemoteKeys()
}