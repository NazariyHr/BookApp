package com.books.app.domain.use_case

import com.books.app.domain.model.Banner
import com.books.app.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(): List<Banner> {
        return repository.getBanners()
    }
}