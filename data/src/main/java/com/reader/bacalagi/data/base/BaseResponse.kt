package com.reader.bacalagi.data.base

data class BaseResponse<T>(
    val status: String,
    val statusCode: Long,
    val message: String,
    val data: T?
)
