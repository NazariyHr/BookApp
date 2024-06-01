package com.books.app.presentation.features.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.domain.model.Book
import com.books.app.domain.use_case.GetAllBooksUseCase
import com.books.app.domain.use_case.GetBannersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getBannersUseCase: GetBannersUseCase
) : ViewModel() {

    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: LibraryState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<LibraryState>(STATE_KEY)!!
        }

    val state = savedStateHandle.getStateFlow(STATE_KEY, LibraryState())

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                stateValue = stateValue.copy(
                    books = getAllBooksUseCase().splitByGenres(),
                    banners = getBannersUseCase()
                )
            }
        }
    }

    private fun List<Book>.splitByGenres(): Map<String, List<Book>> {
        val resultMap = mutableMapOf<String, List<Book>>()
        forEach { book ->
            resultMap[book.genre] = (resultMap[book.genre] ?: emptyList()) + book
        }
        return resultMap
    }
}