package com.reader.bacalagi.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FaqResponse (
    val id: String,
    val question: String,
    val answer: String
)

