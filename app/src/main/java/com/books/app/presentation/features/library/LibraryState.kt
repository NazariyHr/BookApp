package com.books.app.presentation.features.library

import android.os.Parcelable
import com.books.app.domain.model.Banner
import com.books.app.domain.model.Book
import kotlinx.parcelize.Parcelize

typealias GenreName = String

@Parcelize
data class LibraryState(
    val books: Map<GenreName, List<Book>> = emptyMap(),
    val banners: List<Banner> = emptyList()
) : Parcelable