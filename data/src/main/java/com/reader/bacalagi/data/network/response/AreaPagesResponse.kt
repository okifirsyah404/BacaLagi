package com.reader.bacalagi.data.network.response

data class AreaPagesResponse(
    val first: Int,
    val last: Int,
    val current: Int,
    val previous: Any? = null,
    val next: Int
)
