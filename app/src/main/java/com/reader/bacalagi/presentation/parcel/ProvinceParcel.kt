package com.reader.bacalagi.presentation.parcel

import android.os.Parcelable
import com.reader.bacalagi.data.local.model.ProvinceModel
import com.reader.bacalagi.data.local.model.SavedProvinceModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProvinceParcel(
    val code: String,
    val name: String
) : Parcelable {

    fun toModel(): ProvinceModel {
        return ProvinceModel(
            code = code,
            name = name
        )
    }

    companion object {
        fun fromModel(model: ProvinceModel): ProvinceParcel {
            return ProvinceParcel(
                code = model.code,
                name = model.name
            )
        }

        fun fromSavedProvinceModel(model: SavedProvinceModel): ProvinceParcel {
            return ProvinceParcel(
                code = model.code,
                name = model.name
            )
        }

    }
}
