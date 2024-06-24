package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data_area.network.response.ProvinceResponse

@Entity(tableName = "province")
data class ProvinceModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String
) {
    companion object {
        fun fromResponse(response: ProvinceResponse): ProvinceModel {
            return ProvinceModel(
                code = response.code,
                name = response.name
            )
        }
    }
}
