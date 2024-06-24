package com.reader.bacalagi.data_area.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reader.bacalagi.data_area.local.dao.DistrictDao
import com.reader.bacalagi.data_area.local.dao.DistrictRemoteKeyDao
import com.reader.bacalagi.data_area.local.dao.ProvinceDao
import com.reader.bacalagi.data_area.local.dao.ProvinceRemoteKeyDao
import com.reader.bacalagi.data_area.local.dao.RegencyDao
import com.reader.bacalagi.data_area.local.dao.RegencyRemoteKeyDao
import com.reader.bacalagi.data_area.local.dao.SavedDistrictDao
import com.reader.bacalagi.data_area.local.dao.SavedProvinceDao
import com.reader.bacalagi.data_area.local.dao.SavedRegencyDao
import com.reader.bacalagi.data_area.local.model.DistrictModel
import com.reader.bacalagi.data_area.local.model.DistrictRemoteKeyModel
import com.reader.bacalagi.data_area.local.model.ProvinceModel
import com.reader.bacalagi.data_area.local.model.ProvinceRemoteKeyModel
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.data_area.local.model.RegencyRemoteKeyModel
import com.reader.bacalagi.data_area.local.model.SavedDistrictModel
import com.reader.bacalagi.data_area.local.model.SavedProvinceModel
import com.reader.bacalagi.data_area.local.model.SavedRegencyModel

@Database(
    entities = [
        ProvinceModel::class,
        RegencyModel::class,
        DistrictModel::class,
        ProvinceRemoteKeyModel::class,
        RegencyRemoteKeyModel::class,
        DistrictRemoteKeyModel::class,
        SavedProvinceModel::class,
        SavedRegencyModel::class,
        SavedDistrictModel::class
    ], version = 1, exportSchema = false
)
abstract class AreaDatabase : RoomDatabase() {
    abstract fun getProvinceDao(): ProvinceDao
    abstract fun getRegencyDao(): RegencyDao
    abstract fun getDistrictDao(): DistrictDao
    abstract fun getProvinceRemoteKeyDao(): ProvinceRemoteKeyDao
    abstract fun getRegencyRemoteKeyDao(): RegencyRemoteKeyDao
    abstract fun getDistrictRemoteKeyDao(): DistrictRemoteKeyDao
    abstract fun getSavedProvinceDao(): SavedProvinceDao
    abstract fun getSavedRegencyDao(): SavedRegencyDao
    abstract fun getSavedDistrictDao(): SavedDistrictDao
}