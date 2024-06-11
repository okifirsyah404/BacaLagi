package com.reader.bacalagi.data.network.request

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("firebaseTokenId")
    val firebaseTokenID: String,
)
