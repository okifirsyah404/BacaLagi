package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.reader.bacalagi.data.network.response.BookResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.response.UserResponse

@Entity(tableName = "author_product")
data class AuthorProductModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    @Embedded val book: AuthorBookModel,
    @Embedded val user: AuthorUserModel
) {
    companion object {
        fun fromResponse(response: ProductResponse) = AuthorProductModel(
            id = response.id,
            status = response.status,
            finalPrice = response.finalPrice,
            recommendedPrice = response.recommendedPrice,
            description = response.description,
            seenCount = response.seenCount,
            createdAt = response.createdAt,
            book = AuthorBookModel.fromResponse(response.book!!),
            user = AuthorUserModel.fromResponse(response.user!!)
        )

    }
}

data class AuthorBookModel(
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
        fun fromResponse(response: BookResponse) = AuthorBookModel(
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

data class AuthorUserModel(
    val name: String,
    @SerializedName("avatarUrl")
    val avatarURL: String?,
    val phoneNumber: String,
    val adminAreaLocality: String,
    val cityLocality: String,
    val address: String?
) {
    companion object {
        fun fromResponse(response: UserResponse) = AuthorUserModel(
            name = response.profile.name,
            avatarURL = response.profile.avatarURL,
            phoneNumber = response.profile.phoneNumber,
            adminAreaLocality = response.profile.adminAreaLocality,
            cityLocality = response.profile.cityLocality,
            address = response.profile.address
        )
    }

}

@Entity(tableName = "author_product_remote_keys")
data class AuthorProductRemoteKeysModel(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)