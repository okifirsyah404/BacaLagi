package com.reader.bacalagi.data.network.request

import java.io.File

data class PredictProductRequest (
    val buyPrice: String,
    val image: File?
)