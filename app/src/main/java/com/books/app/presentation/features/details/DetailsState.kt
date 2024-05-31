package com.books.app.presentation.features.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(
    val bookId: Int = 0
) : Parcelable