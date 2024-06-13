package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province_remote_keys")
data class ProvinceRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)

