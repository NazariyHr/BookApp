package com.books.app.presentation.features.loading

sealed class LoadingScreenEvent {
    data object GoToLibraryScreen : LoadingScreenEvent()
}