package com.books.app.presentation.features.loading

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoadingState(
    val loadingProgress: Int = 0
) : Parcelable