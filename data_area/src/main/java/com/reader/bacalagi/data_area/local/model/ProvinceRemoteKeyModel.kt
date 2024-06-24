package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province_remote_key")
data class ProvinceRemoteKeyModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
