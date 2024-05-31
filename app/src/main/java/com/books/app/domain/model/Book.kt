package com.books.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: String,
    val image: String,
    val views: String,
    val likes: String,
    val quotes: String
) : Parcelable
