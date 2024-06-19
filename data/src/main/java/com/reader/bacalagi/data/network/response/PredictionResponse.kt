package com.reader.bacalagi.data.network.response

import com.google.gson.annotations.SerializedName

data class PredictionResults(
    val id: String,
    val bookCondition: String,
    val buyPrice: Long,
    val outputPrice: Long,
    val percentage: Long,
    val rippedRatio: Double,
    val wornOutRatio: Double,
    val overallRatio: Double
)