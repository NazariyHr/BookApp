package com.books.app.presentation.features.library

import android.os.Parcelable
import com.books.app.domain.model.Book
import kotlinx.parcelize.Parcelize

@Parcelize
data class LibraryState(
    val books: List<Book> = emptyList()
) : Parcelable