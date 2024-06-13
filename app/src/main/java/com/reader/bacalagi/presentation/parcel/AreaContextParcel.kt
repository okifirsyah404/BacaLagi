package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.utils.enum.AreaContext
import kotlinx.parcelize.Parcelize

@Parcelize
data class AreaContextParcel(
    val areaContext: AreaContext,
    val provinceCode: String? = null,
) : Parcelable