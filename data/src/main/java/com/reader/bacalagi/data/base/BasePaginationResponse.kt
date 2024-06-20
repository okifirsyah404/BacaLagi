package com.reader.bacalagi.data.base

data class BasePaginationResponse<T>(
    val status: String,
    val statusCode: Int,
    val message: String,
    val pagination: Pagination,
    val data: List<T>
)

data class Pagination(
    val limit: Int,
    val page: Int,
    val total: Int
)
