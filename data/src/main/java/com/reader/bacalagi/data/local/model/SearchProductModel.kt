package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.reader.bacalagi.data.network.response.BookResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.response.UserResponse

@Entity(tableName = "search_product")
data class SearchProductModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    @Embedded val book: SearchBookModel,
    @Embedded val user: SearchUserModel
) {
    companion object {
        fun fromResponse(response: ProductResponse) = SearchProductModel(
            id = response.id,
            status = response.status,
            finalPrice = response.finalPrice,
            recommendedPrice = response.recommendedPrice,
            description = response.description,
            seenCount = response.seenCount,
            createdAt = response.createdAt,
            book = SearchBookModel.fromResponse(response.book!!),
            user = SearchUserModel.fromResponse(response.user!!)
        )

    }
}

data class SearchBookModel(
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
        fun fromResponse(response: BookResponse) = SearchBookModel(
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

data class SearchUserModel(
    val name: String,
    @SerializedName("avatarUrl")
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
) {
    companion object {
        fun fromResponse(response: UserResponse) = SearchUserModel(
            name = response.profile.name,
            avatarURL = response.profile.avatarURL,
            phoneNumber = response.profile.phoneNumber,
            adminAreaLocality = response.profile.adminAreaLocality,
            cityLocality = response.profile.cityLocality,
            address = response.profile.address
        )
    }

}

@Entity(tableName = "search_product_remote_keys")
data class SearchProductRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)