package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prediction")
data class PredictionModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "output_price")
    val outputPrice: Int,

    @ColumnInfo(name = "percentage")
    val percentage: Int,

    @ColumnInfo(name = "ripped_ratio")
    val rippedRatio: Double,

    @ColumnInfo(name = "worn_out_ratio")
    val wornOutRatio: Double,

    @ColumnInfo(name = "overall_ratio")
    val overallRatio: Double
)