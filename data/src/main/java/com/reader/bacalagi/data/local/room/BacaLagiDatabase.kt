package com.reader.bacalagi.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reader.bacalagi.data.local.dao.AuthorProductDao
import com.reader.bacalagi.data.local.dao.AuthorProductRemoteKeysDao
import com.reader.bacalagi.data.local.dao.GeneralProductDao
import com.reader.bacalagi.data.local.dao.GeneralProductRemoteKeysDao
import com.reader.bacalagi.data.local.dao.ProvinceDao
import com.reader.bacalagi.data.local.dao.ProvinceRemoteKeysDao
import com.reader.bacalagi.data.local.dao.RegencyDao
import com.reader.bacalagi.data.local.dao.RegencyRemoteKeysDao
import com.reader.bacalagi.data.local.dao.SavedProvinceDao
import com.reader.bacalagi.data.local.dao.SavedRegencyDao
import com.reader.bacalagi.data.local.dao.SearchProductDao
import com.reader.bacalagi.data.local.dao.SearchProductRemoteKeysDao
import com.reader.bacalagi.data.local.model.AuthorProductModel
import com.reader.bacalagi.data.local.model.AuthorProductRemoteKeysModel
import com.reader.bacalagi.data.local.model.GeneralProductModel
import com.reader.bacalagi.data.local.model.GeneralProductRemoteKeysModel
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.ProvinceRemoteKeysModel
import com.reader.bacalagi.data.local.model.RegencyModel
import com.reader.bacalagi.data.local.model.RegencyRemoteKeysModel
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import com.reader.bacalagi.data.local.model.SavedRegencyModel
import com.reader.bacalagi.data.local.model.SearchProductModel
import com.reader.bacalagi.data.local.model.SearchProductRemoteKeysModel
import com.reader.bacalagi.data.utils.Converters

@Database(
    entities = [ProvinceRemoteKeysModel::class, RegencyRemoteKeysModel::class, ProvinceModel::class, RegencyModel::class, SavedProvinceModel::class, SavedRegencyModel::class, GeneralProductModel::class, GeneralProductRemoteKeysModel::class, SearchProductModel::class, SearchProductRemoteKeysModel::class, AuthorProductModel::class, AuthorProductRemoteKeysModel::class],
    version = 9,
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

    abstract fun getGeneralProductDao(): GeneralProductDao

    abstract fun getGeneralProductRemoteKeysDao(): GeneralProductRemoteKeysDao

    abstract fun getSearchProductDao(): SearchProductDao

    abstract fun getSearchProductRemoteKeysDao(): SearchProductRemoteKeysDao

    abstract fun getAuthorProductDao(): AuthorProductDao

    abstract fun getAuthorProductRemoteKeysDao(): AuthorProductRemoteKeysDao

}