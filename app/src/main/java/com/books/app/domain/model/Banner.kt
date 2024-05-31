package com.books.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val bookId: Int,
    val imageUrl: String
) : Parcelable