package com.reader.bacalagi.data_area.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data_area.network.response.DistrictResponse

@Entity(tableName = "district")
data class DistrictModel(
    @PrimaryKey(autoGenerate = false)
    val code: String,
    val name: String,
    val regencyCode: String
) {
    companion object {
        fun fromResponse(response: DistrictResponse) = DistrictModel(
            code = response.code,
            name = response.name,
            regencyCode = response.regencyCode
        )
    }
}
