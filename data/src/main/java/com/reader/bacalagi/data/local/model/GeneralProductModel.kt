package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.reader.bacalagi.data.network.response.BookResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.response.UserResponse

@Entity(tableName = "general_product")
data class GeneralProductModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    @Embedded val book: GeneralBookModel,
    @Embedded val user: GeneralUserModel
) {
    companion object {
        fun fromResponse(response: ProductResponse) = GeneralProductModel(
            id = response.id,
            status = response.status,
            finalPrice = response.finalPrice,
            recommendedPrice = response.recommendedPrice,
            description = response.description,
            seenCount = response.seenCount,
            createdAt = response.createdAt,
            book = GeneralBookModel.fromResponse(response.book!!),
            user = GeneralUserModel.fromResponse(response.user!!)
        )

    }
}

data class GeneralBookModel(
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
    val publishYear: Int,
    val language: String,
    @SerializedName("imageUrl")
    val imageURL: String,
    val buyPrice: Long
) {
    companion object {
        fun fromResponse(response: BookResponse) = GeneralBookModel(
            title = response.title,
            author = response.author,
            isbn = response.isbn,
            publisher = response.publisher,
            publishYear = response.publishYear,
            language = response.language,
            imageURL = response.imageURL,
            buyPrice = response.buyPrice
        )
    }
}

data class GeneralUserModel(
    val name: String,
    @SerializedName("avatarUrl")
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
) {
    companion object {
        fun fromResponse(response: UserResponse) = GeneralUserModel(
            name = response.profile.name,
            avatarURL = response.profile.avatarURL,
            phoneNumber = response.profile.phoneNumber,
            adminAreaLocality = response.profile.adminAreaLocality,
            cityLocality = response.profile.cityLocality,
            address = response.profile.address
        )
    }

}

@Entity(tableName = "general_product_remote_keys")
data class GeneralProductRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)


