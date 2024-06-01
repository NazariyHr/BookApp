package com.books.app.presentation.features.details

sealed class DetailsScreenAction {
    data class OnBookChanged(val bookId: Int) : DetailsScreenAction()
}