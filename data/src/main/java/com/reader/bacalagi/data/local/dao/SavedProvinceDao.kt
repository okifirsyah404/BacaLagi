package com.reader.bacalagi.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data.local.model.SavedProvinceModel

@Dao
interface SavedProvinceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaved(users: SavedProvinceModel)

    @Query("SELECT * FROM saved_province WHERE id = 0")
    fun getSaved(): LiveData<SavedProvinceModel?>

    @Query("DELETE FROM saved_province")
    suspend fun deleteAll()
}