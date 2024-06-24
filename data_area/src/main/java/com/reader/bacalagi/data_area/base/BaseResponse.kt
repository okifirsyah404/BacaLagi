package com.reader.bacalagi.data_area.base

import com.reader.bacalagi.data_area.network.response.MetaResponse

data class BaseResponse<T>(
    val statusCode: Long,
    val message: String,
    val data: T,
    val meta: MetaResponse,
)
