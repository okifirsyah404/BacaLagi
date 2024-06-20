package com.reader.bacalagi.data.network.request

import java.io.File

data class PostProductRequest(
    val title: String,
    val author: String,
    val publisher: String,
    val publishYear: String,
    val buyPrice: String,
    val finalPrice: String,
    val ISBN: String,
    val language: String,
    val description: String,
    val image: File?
)