package com.reader.bacalagi.data_area.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reader.bacalagi.data_area.local.model.SavedDistrictModel

@Dao
interface SavedDistrictDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaved(users: SavedDistrictModel)

    @Query("SELECT * FROM saved_district WHERE id = 0")
    fun getSaved(): LiveData<SavedDistrictModel?>

    @Query("DELETE FROM saved_district")
    suspend fun deleteAll()
}