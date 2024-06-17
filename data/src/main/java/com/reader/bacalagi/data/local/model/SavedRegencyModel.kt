package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_regency")
data class SavedRegencyModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val code: String,
    val name: String,
    val provinceCode: String
) {
    companion object {
        fun fromRegency(regency: RegencyModel): SavedRegencyModel {
            return SavedRegencyModel(
                code = regency.code,
                name = regency.name,
                provinceCode = regency.provinceCode
            )
        }
    }
}