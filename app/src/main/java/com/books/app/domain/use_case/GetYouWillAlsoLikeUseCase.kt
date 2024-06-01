package com.books.app.domain.use_case

import com.books.app.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetYouWillAlsoLikeUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(bookId: Int): List<Int> {
        return repository.getYouWillAlsoLike(bookId)
    }
}