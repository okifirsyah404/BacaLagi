package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.data_area.local.model.RegencyModel
import com.reader.bacalagi.data_area.local.model.SavedRegencyModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegencyParcel(
    val code: String,
    val name: String,
    val provinceCode: String
) : Parcelable {

    fun toModel(): RegencyModel {
        return RegencyModel(
            code = code,
            name = name,
            provinceCode = provinceCode
        )
    }

    companion object {
        fun fromModel(model: RegencyModel): RegencyParcel {
            return RegencyParcel(
                code = model.code,
                name = model.name,
                provinceCode = model.provinceCode
            )
        }

        fun fromSavedRegencyModel(model: SavedRegencyModel): RegencyParcel {
            return RegencyParcel(
                code = model.code,
                name = model.name,
                provinceCode = model.provinceCode
            )
        }
    }
}