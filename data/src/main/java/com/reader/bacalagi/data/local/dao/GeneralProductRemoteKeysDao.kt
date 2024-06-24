package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.GeneralProductRemoteKeysModel

@Dao
interface GeneralProductRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<GeneralProductRemoteKeysModel>)

    @Query("SELECT * FROM general_product_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): GeneralProductRemoteKeysModel?

    @Query("DELETE FROM general_product_remote_keys")
    suspend fun deleteRemoteKeys()
}