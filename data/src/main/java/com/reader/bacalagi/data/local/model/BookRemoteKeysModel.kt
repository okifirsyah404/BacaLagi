package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_remote_keys")
data class BookRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)