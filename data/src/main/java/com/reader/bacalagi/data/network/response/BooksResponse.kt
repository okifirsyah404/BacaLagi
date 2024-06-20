package com.reader.bacalagi.data.network.response

data class BooksResponse(
    val id: String,
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
    val publishYear: Long,
    val language: String,
    val imageURL: String,
    val buyPrice: Long,
    val predictionResults: PredictionResults
)