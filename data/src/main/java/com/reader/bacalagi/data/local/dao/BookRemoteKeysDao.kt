package com.reader.bacalagi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.BookRemoteKeysModel

@Dao
interface BookRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<BookRemoteKeysModel>)

    @Query("SELECT * FROM book_remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String?): BookRemoteKeysModel?

    @Query("DELETE FROM book_remote_keys")
    suspend fun deleteRemoteKeys()
}