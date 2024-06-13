package com.reader.bacalagi.data.base

data class BaseResponse<T>(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: T?
)
