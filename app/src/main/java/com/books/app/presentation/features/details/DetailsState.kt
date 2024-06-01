package com.books.app.presentation.features.details

import android.os.Parcelable
import com.books.app.domain.model.Book
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(
    val bookIdWhileInit: Int = -1,
    val selectedBook: Book? = null,
    val books: List<Book> = emptyList(),
    val booksWillAlsoLike: List<Book> = emptyList()
) : Parcelable