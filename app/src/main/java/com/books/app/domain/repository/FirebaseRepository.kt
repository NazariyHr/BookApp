package com.books.app.domain.repository

import com.books.app.domain.model.Banner
import com.books.app.domain.model.Book

interface FirebaseRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun getBooksListForDetails(): List<Book>
    suspend fun getBanners(): List<Banner>
    suspend fun getYouWillAlsoLike(bookId: Int): List<Int>
}