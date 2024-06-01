package com.books.app.presentation.features.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.domain.use_case.GetBooksByIdsUseCase
import com.books.app.domain.use_case.GetBooksInDetailsUseCase
import com.books.app.domain.use_case.GetYouWillAlsoLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getBooksInDetailsUseCase: GetBooksInDetailsUseCase,
    private val getBooksByIdsUseCase: GetBooksByIdsUseCase,
    private val getYouWillAlsoLikeUseCase: GetYouWillAlsoLikeUseCase
) : ViewModel() {

    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: DetailsState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<DetailsState>(STATE_KEY)!!
        }

    val state = savedStateHandle.getStateFlow(STATE_KEY, DetailsState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val books = getBooksInDetailsUseCase()
            stateValue = stateValue.copy(
                books = books
            )
        }
    }

    fun saveBookId(bookId: Int) {
        viewModelScope.launch {
            val books = getBooksInDetailsUseCase()
            stateValue = stateValue.copy(
                bookIdWhileInit = bookId,
                books = books,
                selectedBook = books.find { it.id == bookId },
                booksWillAlsoLike = getBooksByIdsUseCase(getYouWillAlsoLikeUseCase(bookId))
            )
        }
    }

    fun onAction(action: DetailsScreenAction) {
        when (action) {
            is DetailsScreenAction.OnBookChanged -> {
                loadBookInfo(action.bookId)
            }
        }
    }

    private fun loadBookInfo(bookId: Int) {
        viewModelScope.launch {
            stateValue = stateValue.copy(
                selectedBook = stateValue.books.find { it.id == bookId },
                booksWillAlsoLike = getBooksByIdsUseCase(getYouWillAlsoLikeUseCase(bookId))
            )
        }
    }
}