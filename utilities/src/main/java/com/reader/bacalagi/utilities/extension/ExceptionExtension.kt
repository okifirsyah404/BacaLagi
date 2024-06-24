package com.reader.bacalagi.utilities.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reader.bacalagi.utilities.base.BaseErrorResponse
import retrofit2.HttpException
import java.io.IOException

fun Exception.createErrorResponse(): String {
    return when (this) {
        is IOException -> "Network error occurred"
        is HttpException -> {
            val errorBody = this.getHttpBodyErrorMessage()
            errorBody
        }

        else -> "Unknown error occurred"
    }
}

fun HttpException.getHttpBodyErrorMessage(): String {

    val errorBody = this.response()?.errorBody()
    val defaultMessage = "HTTP error occurred"

    return when (this.code()) {
        in 500..599 -> {
            // Handle 5xx server error codes specifically
            "Server error occurred"
        }

        401 -> {
            // Handle unauthorized error
            "Unauthorized"
        }

        404 -> {
            // Handle not found error
            "Not found"
        }

        else -> {
            // Handle other error codes
            val gson = Gson()
            val type = object : TypeToken<BaseErrorResponse<Nothing>>() {}.type
            val errorResponse: BaseErrorResponse<Nothing>? = errorBody?.let {
                gson.fromJson(it.charStream(), type)
            }
            errorResponse?.message ?: defaultMessage

        }
    }
}