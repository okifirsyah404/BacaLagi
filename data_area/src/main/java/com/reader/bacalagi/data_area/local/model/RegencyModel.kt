package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data_area.network.response.RegencyResponse

@Entity(tableName = "regency")
data class RegencyModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String,
    val provinceCode: String
) {
    companion object {
        fun fromResponse(response: RegencyResponse) = RegencyModel(
            code = response.code,
            name = response.name,
            provinceCode = response.provinceCode
        )
    }
}
