package com.reader.bacalagi.data.local.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithBookModel(
    @Embedded val book: BookModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id"
    )
    val product: ProductModel,
)


