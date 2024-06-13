package com.reader.bacalagi.data.utils.extension

import retrofit2.HttpException
import java.io.IOException


fun Exception.createErrorResponse(): String {
    return when (this) {
        is IOException -> "Network error occurred"
        is HttpException -> {
            val errorBody = response()?.errorBody()?.string()
            errorBody ?: "HTTP error occurred"
        }

        else -> "Unknown error occurred"
    }
}