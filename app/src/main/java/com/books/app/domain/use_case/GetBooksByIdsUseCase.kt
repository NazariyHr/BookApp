package com.books.app.domain.use_case

import com.books.app.domain.model.Book
import com.books.app.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetBooksByIdsUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(ids: List<Int>): List<Book> {
        return repository.getAllBooks().filter { it.id in ids }
    }
}