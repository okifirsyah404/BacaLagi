package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "regency_remote_keys")
data class RegencyRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
