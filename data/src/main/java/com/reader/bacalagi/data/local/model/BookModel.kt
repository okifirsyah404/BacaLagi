package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
    @ColumnInfo(name = "publish_year")
    val publishYear: Int,
    val language: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "buy_price")
    val buyPrice: Long
)
