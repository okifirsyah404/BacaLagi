package com.reader.bacalagi.data.network.response

data class PrivacyPolicyResponse (
    val status: String,
    val statusCode: Long,
    val message: String,
    val data: List<ListPolicy>
)

data class ListPolicy (
    val id: String,
    val title: String,
    val content: String
)
