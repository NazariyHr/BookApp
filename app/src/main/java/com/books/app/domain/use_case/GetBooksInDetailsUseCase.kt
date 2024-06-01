package com.books.app.domain.use_case

import com.books.app.domain.model.Book
import com.books.app.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetBooksInDetailsUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(): List<Book> {
        return repository.getBooksListForDetails()
    }
}