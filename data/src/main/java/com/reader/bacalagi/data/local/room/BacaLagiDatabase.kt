package com.reader.bacalagi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reader.bacalagi.data.local.dao.BookDao
import com.reader.bacalagi.data.local.dao.BookRemoteKeysDao
import com.reader.bacalagi.data.local.dao.ProvinceDao
import com.reader.bacalagi.data.local.dao.ProvinceRemoteKeysDao
import com.reader.bacalagi.data.local.dao.RegencyDao
import com.reader.bacalagi.data.local.dao.RegencyRemoteKeysDao
import com.reader.bacalagi.data.local.dao.SavedProvinceDao
import com.reader.bacalagi.data.local.dao.SavedRegencyDao
import com.reader.bacalagi.data.local.model.BookModel
import com.reader.bacalagi.data.local.model.BookRemoteKeysModel
import com.reader.bacalagi.data.local.model.ProductModel
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import com.reader.bacalagi.data.local.model.SavedRegencyModel
import com.reader.bacalagi.data.utils.Converters

@Database(
    entities = [ProvinceRemoteKeysModel::class, RegencyRemoteKeysModel::class, ProvinceModel::class, RegencyModel::class, SavedProvinceModel::class, SavedRegencyModel::class, ProductModel::class, BookRemoteKeysModel::class, BookModel::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BacaLagiDatabase : RoomDatabase() {
    abstract fun getProvinceRemoteKeysDao(): ProvinceRemoteKeysDao

    abstract fun getRegencyRemoteKeysDao(): RegencyRemoteKeysDao

    abstract fun getProvinceDao(): ProvinceDao

    abstract fun getRegencyDao(): RegencyDao

    abstract fun getSavedProvinceDao(): SavedProvinceDao

    abstract fun getSavedRegencyDao(): SavedRegencyDao

    abstract fun getBookDao(): BookDao

    abstract fun bookRemoteKeysDao(): BookRemoteKeysDao
}