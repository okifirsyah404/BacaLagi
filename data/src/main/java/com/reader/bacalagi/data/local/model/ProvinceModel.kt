package com.reader.bacalagi.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data.network.response.AreaProvinceResponse

@Entity(tableName = "province")
data class ProvinceModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String
) {
    companion object {
        fun fromResponse(response: AreaProvinceResponse): ProvinceModel {
            return ProvinceModel(
                code = response.code,
                name = response.name
            )
        }
    }
}
