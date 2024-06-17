package com.reader.bacalagi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reader.bacalagi.data.local.dao.ProvinceDao
import com.reader.bacalagi.data.local.dao.ProvinceRemoteKeysDao
import com.reader.bacalagi.data.local.dao.RegencyDao
import com.reader.bacalagi.data.local.dao.RegencyRemoteKeysDao
import com.reader.bacalagi.data.local.dao.SavedProvinceDao
import com.reader.bacalagi.data.local.dao.SavedRegencyDao
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import com.reader.bacalagi.data.local.model.SavedRegencyModel

@Database(
    entities = [ProvinceRemoteKeysModel::class, RegencyRemoteKeysModel::class, ProvinceModel::class, RegencyModel::class, SavedProvinceModel::class, SavedRegencyModel::class],
    version = 3,
    exportSchema = false
)
abstract class BacaLagiDatabase : RoomDatabase() {
    abstract fun getProvinceRemoteKeysDao(): ProvinceRemoteKeysDao

    abstract fun getRegencyRemoteKeysDao(): RegencyRemoteKeysDao

    abstract fun getProvinceDao(): ProvinceDao

    abstract fun getRegencyDao(): RegencyDao

    abstract fun getSavedProvinceDao(): SavedProvinceDao

    abstract fun getSavedRegencyDao(): SavedRegencyDao
}