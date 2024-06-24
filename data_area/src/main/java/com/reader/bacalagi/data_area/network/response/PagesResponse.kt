package com.reader.bacalagi.data_area.network.response

data class PagesResponse(
    val first: Int,
    val last: Int,
    val current: Int,
    val previous: Int? = null,
    val next: Int
)
