package com.reader.bacalagi.data.network.response

import com.reader.bacalagi.data.network.enum.BookCondition


data class PredictionResponse(
    val id: String,
    val bookCondition: BookCondition,
    val buyPrice: Long,
    val outputPrice: Long,
    val percentage: Int,
    val rippedRatio: Double,
    val wornOutRatio: Double,
    val overallRatio: Double
)
