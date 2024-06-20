package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.reader.bacalagi.data.network.response.ProductResponse

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = BookModel::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    @ColumnInfo(name = "final_price")
    val finalPrice: Int,
    @ColumnInfo(name = "recommended_price")
    val recommendedPrice: Long,
    val description: String,
    @ColumnInfo(name = "seen_count")
    val seenCount: Long,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @Embedded(prefix = "book_")
    val book: BookModel?
) {
    companion object {
        fun fromResponse(response: ProductResponse) = ProductModel(
            id = response.id,
            status = response.status,
            finalPrice = response.finalPrice,
            recommendedPrice = response.recommendedPrice,
            description = response.description,
            seenCount = response.seenCount,
            createdAt = response.createdAt,
            book = BookModel(
                id = response.books.id,
                title = response.books.title,
                author = response.books.author,
                isbn = response.books.ISBN,
                publisher = response.books.publisher,
                publishYear = response.books.publishYear,
                language = response.books.language,
                imageUrl = response.books.imageUrl,
                buyPrice = response.books.buyPrice
            )
        )
    }
}
