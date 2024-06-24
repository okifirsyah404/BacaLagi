package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_district")
data class SavedDistrictModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val code: String,
    val name: String,
    val regencyCode: String
) {
    companion object {
        fun fromDistrictModel(district: DistrictModel): SavedDistrictModel {
            return SavedDistrictModel(
                code = district.code,
                name = district.name,
                regencyCode = district.regencyCode
            )
        }
    }
}
