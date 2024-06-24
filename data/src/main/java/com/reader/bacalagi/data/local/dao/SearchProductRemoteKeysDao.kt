package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.SearchProductRemoteKeysModel

@Dao
interface SearchProductRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<SearchProductRemoteKeysModel>)

    @Query("SELECT * FROM search_product_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): SearchProductRemoteKeysModel?

    @Query("DELETE FROM search_product_remote_keys")
    suspend fun deleteRemoteKeys()
}