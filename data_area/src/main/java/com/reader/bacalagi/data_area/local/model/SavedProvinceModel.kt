package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_province")
data class SavedProvinceModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val code: String,
    val name: String
) {
    companion object {
        fun fromProvinceModel(province: ProvinceModel): SavedProvinceModel {
            return SavedProvinceModel(
                code = province.code,
                name = province.name
            )
        }
    }
}
