package com.reader.bacalagi.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reader.bacalagi.data.network.response.BooksResponse

class Converters {
    @TypeConverter
    fun fromBookList(books: List<BooksResponse>?): String? {
        return Gson().toJson(books)
    }

    @TypeConverter
    fun toBookList(booksString: String?): List<BooksResponse>? {
        val listType = object : TypeToken<List<BooksResponse>>() {}.type
        return Gson().fromJson(booksString, listType)
    }
}
