package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.AuthorProductRemoteKeysModel

@Dao
interface AuthorProductRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<AuthorProductRemoteKeysModel>)

    @Query("SELECT * FROM author_product_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): AuthorProductRemoteKeysModel?

    @Query("DELETE FROM province_remote_keys")
    suspend fun deleteRemoteKeys()
}