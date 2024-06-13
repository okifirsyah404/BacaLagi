package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data.network.response.AreaRegenciesResponse

@Entity(tableName = "regency")
data class RegencyModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String,
    val provinceCode: String
) {
    companion object {
        fun fromResponse(response: AreaRegenciesResponse) = RegencyModel(
            code = response.code,
            name = response.name,
            provinceCode = response.provinceCode
        )
    }
}
