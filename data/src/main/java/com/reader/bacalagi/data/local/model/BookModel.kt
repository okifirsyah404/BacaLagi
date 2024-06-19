package com.reader.bacalagi.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reader.bacalagi.data.network.response.BooksResponse
@Entity(tableName = "book")
data class BookModel(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
    val publishYear: Long,
    val language: String,
    val imageURL: String,
    val buyPrice: Long
) {
    companion object {
        fun fromResponse(response: BooksResponse) = BookModel(
            id = response.id,
        title = response.title,
        author = response.author,
        isbn = response.isbn,
        publisher= response.publisher,
        publishYear= response.publishYear,
        language= response.language,
        imageURL= response.imageURL,
        buyPrice= response.buyPrice
        )
    }
}
