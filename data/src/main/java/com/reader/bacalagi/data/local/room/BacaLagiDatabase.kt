package com.reader.bacalagi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reader.bacalagi.data.local.dao.ProvinceDao
import com.reader.bacalagi.data.local.dao.ProvinceRemoteKeysDao
import com.reader.bacalagi.data.local.dao.RegencyDao
import com.reader.bacalagi.data.local.dao.RegencyRemoteKeysDao
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel

@Database(
    entities = [ProvinceRemoteKeysModel::class, RegencyRemoteKeysModel::class, ProvinceModel::class, RegencyModel::class],
    version = 1,
    exportSchema = false
)
abstract class BacaLagiDatabase : RoomDatabase() {
    abstract fun getProvinceRemoteKeysDao(): ProvinceRemoteKeysDao

    abstract fun getRegencyRemoteKeysDao(): RegencyRemoteKeysDao

    abstract fun getProvinceDao(): ProvinceDao

    abstract fun getRegencyDao(): RegencyDao
}