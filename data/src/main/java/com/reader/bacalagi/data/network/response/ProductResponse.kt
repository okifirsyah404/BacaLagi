package com.reader.bacalagi.data.network.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	val id: String,
	val status: String,
	val finalPrice: Int,
	val recommendedPrice: Long,
	val description: String,
	val seenCount: Long,
	val createdAt: String,
	val books: BooksResponse,
	val user: UserResponse
)




