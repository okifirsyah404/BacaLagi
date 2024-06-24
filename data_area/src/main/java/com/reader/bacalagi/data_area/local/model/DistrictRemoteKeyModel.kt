package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "district_remote_key")
data class DistrictRemoteKeyModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
