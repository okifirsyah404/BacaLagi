package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Relation
import com.reader.bacalagi.data.network.response.BooksResponse
import com.reader.bacalagi.data.network.response.ProductResponse
import com.reader.bacalagi.data.network.response.UserResponse

@Entity(
    tableName = "product",
    foreignKeys = [
        ForeignKey(
            entity = BookModel::class,
            parentColumns = ["id"],
            childColumns = ["books"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    val books: List<BooksResponse>
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
            books = listOf(response.books)
        )
    }
}
