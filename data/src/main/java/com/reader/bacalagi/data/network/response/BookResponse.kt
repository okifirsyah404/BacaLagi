package com.reader.bacalagi.data.network.response

import com.google.gson.annotations.SerializedName

data class BookResponse(
    val id: String,
    val title: String,
    val author: String,
    @SerializedName("ISBN")
    val isbn: String,
    val publisher: String,
    val publishYear: Int,
    val language: String,
    @SerializedName("imageUrl")
    val imageURL: String,
    val buyPrice: Long,
    val predictionResults: PredictionResults?
)