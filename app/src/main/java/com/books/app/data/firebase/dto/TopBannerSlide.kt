package com.books.app.data.firebase.dto

import com.books.app.domain.model.Banner
import com.google.gson.annotations.SerializedName

data class TopBannerSlide(
    val id: Int,
    @SerializedName("book_id")
    val bookId: Int,
    val cover: String
)

fun TopBannerSlide.toBanner(): Banner {
    return Banner(
        id, bookId, cover
    )
}