package com.books.app.presentation.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object Loading : Screen()

    @Serializable
    data object Library : Screen()

    @Serializable
    @Parcelize
    data class Details(val bookId: Int) : Screen(), Parcelable
}