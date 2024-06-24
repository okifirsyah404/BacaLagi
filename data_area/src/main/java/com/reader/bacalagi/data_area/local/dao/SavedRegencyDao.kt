package com.reader.bacalagi.data_area.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.SavedRegencyModel

@Dao
interface SavedRegencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaved(users: SavedRegencyModel)

    @Query("SELECT * FROM saved_regency WHERE id = 0")
    fun getSaved(): LiveData<SavedRegencyModel?>

    @Query("DELETE FROM saved_regency")
    suspend fun deleteAll()
}