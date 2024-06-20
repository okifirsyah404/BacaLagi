package com.reader.bacalagi.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FaqResponse (
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("statusCede")
    val statusCode: Long? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("ListQuestion")
    val ListQuestion: List<ListQuestion>? = null
)
data class ListQuestion(

    val id: String,
    val question: String,
    val answer: String

)

