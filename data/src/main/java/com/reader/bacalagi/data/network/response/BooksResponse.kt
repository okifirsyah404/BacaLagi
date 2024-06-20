package com.reader.bacalagi.data.network.response


data class BooksResponse(
    val id: String,
    val title: String,
    val author: String,
    val ISBN: String,
    val publisher: String,
    val publishYear: Int,
    val language: String,
    val imageUrl: String,
    val buyPrice: Long,
    val predictionResults: PredictionResults
)
