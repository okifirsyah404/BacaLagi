package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.utils.enum.FailedContext
import kotlinx.parcelize.Parcelize

@Parcelize
data class FailedParcel(
    val context: FailedContext,
    val message: String? = null
) : Parcelable
