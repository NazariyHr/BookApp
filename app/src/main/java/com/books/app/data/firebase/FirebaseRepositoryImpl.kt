package com.books.app.data.firebase

import com.books.app.data.firebase.dto.RemoteConfigResponse
import com.books.app.data.firebase.dto.toBanner
import com.books.app.data.firebase.dto.toBook
import com.books.app.domain.errors.DataLoadingFailed
import com.books.app.domain.model.Banner
import com.books.app.domain.model.Book
import com.books.app.domain.repository.FirebaseRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRepositoryImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseRepository {

    private var memoryResponseCache: RemoteConfigResponse? = null

    override suspend fun getAllBooks(): List<Book> {
        return getConfigInfo().books.map { it.toBook() }
    }

    override suspend fun getBanners(): List<Banner> {
        return getConfigInfo().topBannerSlides.map { it.toBanner() }
    }

    override suspend fun getYouWillAlsoLike(): List<Int> {
        return getConfigInfo().youWillLikeSection
    }

    private suspend fun getConfigInfo(): RemoteConfigResponse {
        return if (memoryResponseCache != null) {
            memoryResponseCache!!
        } else {
            val config = getConfigInfoFromFirebase()
            memoryResponseCache = config
            config
        }
    }

    private suspend fun getConfigInfoFromFirebase(): RemoteConfigResponse {
        return suspendCoroutine { continuation ->
            remoteConfig
                .fetchAndActivate()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val value = remoteConfig.getValue("json_data")
                        val config =
                            Gson().fromJson(value.asString(), RemoteConfigResponse::class.java)
                        continuation.resume(config)
                    } else {
                        continuation.resumeWithException(DataLoadingFailed("Failed to load data"))
                    }
                }
        }
    }
}