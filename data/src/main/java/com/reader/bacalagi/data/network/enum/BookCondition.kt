package com.reader.bacalagi.data.network.enum

import com.google.gson.annotations.SerializedName

enum class BookCondition(val value: String) {

    @SerializedName("LIKE_NEW")
    LIKE_NEW("Like New"),

    @SerializedName("VERY_GOOD")
    GOOD("Good"),

    @SerializedName("QUITE_GOOD")
    QUITE_GOOD("Quite Good"),

    @SerializedName("FAIR")
    FAIR("Fair"),

    @SerializedName("POOR")
    POOR("Poor"), ;
}