package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data.network.response.ProductResponse

@Entity(
    tableName = "product",
//    foreignKeys = [
//        ForeignKey(
//            entity = BookModel::class,
//            parentColumns = ["id"],
//            childColumns = ["book_id"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class ProductModel(
    @ColumnInfo(name = "product_id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val finalPrice: Long,
    val recommendedPrice: Long,
    val description: String,
    val seenCount: Long,
    val createdAt: String,
    @Embedded val book: BookModel?,
//    @ColumnInfo(name = "book_id")
//    val bookId: String?
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
            book = response.book?.let { BookModel.fromResponse(it) },
        )
    }
}



