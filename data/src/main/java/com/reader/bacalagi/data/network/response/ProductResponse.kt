package com.reader.bacalagi.data.network.response

data class ProductResponse(
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    val book: BookResponse?,
    val user: UserResponse?
)




