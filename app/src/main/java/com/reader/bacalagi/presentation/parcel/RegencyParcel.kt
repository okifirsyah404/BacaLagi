package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.data.local.model.RegencyModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegencyParcel(
    val code: String,
    val name: String,
    val provinceCode: String
) : Parcelable {
    companion object {
        fun fromModel(model: RegencyModel): RegencyParcel {
            return RegencyParcel(
                code = model.code,
                name = model.name,
                provinceCode = model.provinceCode
            )
        }
    }
}