package com.reader.bacalagi.utilities.base

data class BaseErrorResponse<T>(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: T?
)
