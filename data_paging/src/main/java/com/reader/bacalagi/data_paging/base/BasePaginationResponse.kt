package com.reader.bacalagi.data_paging.base

data class BasePaginationResponse<T>(
    val status: String,
    val statusCode: Int,
    val message: String,
    val pagination: PaginationMeta,
    val data: List<T>
)
