package com.books.app.data.firebase.dto

import com.books.app.domain.model.Book
import com.google.gson.annotations.SerializedName

data class BookDto(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: String,
    @SerializedName("cover_url")
    val coverUrl: String,
    val views: String,
    val likes: String,
    val quotes: String
)

fun BookDto.toBook(): Book {
    return Book(
        id, name, author, summary, genre, coverUrl, views, likes, quotes
    )
}