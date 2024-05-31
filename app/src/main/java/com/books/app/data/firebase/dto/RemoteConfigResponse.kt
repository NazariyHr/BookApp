package com.books.app.data.firebase.dto

import com.google.gson.annotations.SerializedName

data class RemoteConfigResponse(
    val books: List<BookDto>,
    @SerializedName("top_banner_slides")
    val topBannerSlides: List<TopBannerSlide>,
    @SerializedName("you_will_like_section")
    val youWillLikeSection: List<Int>
)