package com.books.app.data.firebase

import com.books.app.data.firebase.dto.DetailsCarouselResponse
import com.books.app.data.firebase.dto.MainConfigResponse
import com.books.app.data.firebase.dto.toBanner
import com.books.app.data.firebase.dto.toBook
import com.books.app.domain.errors.DataLoadingFailed
import com.books.app.domain.model.Banner
import com.books.app.domain.model.Book
import com.books.app.domain.repository.FirebaseRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseRepository {

    private var memoryResponseCache: MainConfigResponse? = null
    private var memoryDetailCarouselResponseCache: DetailsCarouselResponse? = null

    override suspend fun getAllBooks(): List<Book> {
        return getConfigInfo().books.map { it.toBook() }
    }

    override suspend fun getBanners(): List<Banner> {
        return getConfigInfo().topBannerSlides.map { it.toBanner() }
    }

    override suspend fun getYouWillAlsoLike(bookId: Int): List<Int> {
        return getConfigInfo().youWillLikeSection
    }

    override suspend fun getBooksListForDetails(): List<Book> {
        return getDetailCarousel().books.map { it.toBook() }
    }

    private suspend fun getConfigInfo(): MainConfigResponse {
        return if (memoryResponseCache != null) {
            memoryResponseCache!!
        } else {
            val config = getConfigInfoFromFirebase()
            memoryResponseCache = config
            config
        }
    }

    private suspend fun getDetailCarousel(): DetailsCarouselResponse {
        return if (memoryDetailCarouselResponseCache != null) {
            memoryDetailCarouselResponseCache!!
        } else {
            val details = getDetailCarouselFromFirebase()
            memoryDetailCarouselResponseCache = details
            details
        }
    }

    private suspend fun getConfigInfoFromFirebase(): MainConfigResponse {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                remoteConfig
                    .fetchAndActivate()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val value = remoteConfig.getValue("json_data")
                            val config =
                                Gson().fromJson(value.asString(), MainConfigResponse::class.java)
                            continuation.resume(config)
                        } else {
                            continuation.resumeWithException(DataLoadingFailed("Failed to load data"))
                        }
                    }
            }
        }
    }

    private suspend fun getDetailCarouselFromFirebase(): DetailsCarouselResponse {
        return suspendCoroutine { continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                remoteConfig
                    .fetchAndActivate()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val value = remoteConfig.getValue("details_carousel")
                            val result =
                                Gson().fromJson(
                                    value.asString(),
                                    DetailsCarouselResponse::class.java
                                )
                            continuation.resume(result)
                        } else {
                            continuation.resumeWithException(DataLoadingFailed("Failed to load data"))
                        }
                    }
            }
        }
    }
}