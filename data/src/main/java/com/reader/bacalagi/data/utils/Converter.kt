package com.reader.bacalagi.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reader.bacalagi.data.network.response.BookResponse

class Converters {
    @TypeConverter
    fun fromBookList(books: List<BookResponse>?): String? {
        return Gson().toJson(books)
    }

    @TypeConverter
    fun toBookList(booksString: String?): List<BookResponse>? {
        val listType = object : TypeToken<List<BookResponse>>() {}.type
        return Gson().fromJson(booksString, listType)
    }
}
