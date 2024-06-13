package com.reader.bacalagi.data.base

import com.reader.bacalagi.data.network.response.AreaPaginationMetaResponse

data class BaseAreaResponse<T>(
    val statusCode: Long,
    val message: String,
    val data: T,
    val meta: AreaPaginationMetaResponse,
)