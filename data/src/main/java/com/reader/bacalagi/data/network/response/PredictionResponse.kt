package com.reader.bacalagi.data.network.response


data class PredictionResponse(
    val id: String,
    val bookCondition: String,
    val buyPrice: Long,
    val outputPrice: Long,
    val percentage: Long,
    val rippedRatio: Double,
    val wornOutRatio: Double,
    val overallRatio: Double
)
