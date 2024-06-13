package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.data.local.model.ProvinceModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProvinceParcel(
    val code: String,
    val name: String
) : Parcelable {
    companion object {
        fun fromModel(model: ProvinceModel): ProvinceParcel {
            return ProvinceParcel(
                code = model.code,
                name = model.name
            )
        }
    }
}
