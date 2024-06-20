package com.reader.bacalagi.data.network.request

import java.io.File

data class EditProductRequest (
    val title: String,
    val author: String,
    val publisher: String,
    val publishYear: Long,
    val buyPrice: Long,
    val finalPrice: Long,
    val ISBN: String,
    val language: String,
    val description: String,
    val image: File?
)
