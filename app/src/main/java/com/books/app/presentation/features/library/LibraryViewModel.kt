package com.books.app.presentation.features.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
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

    fun onAction(action: LibraryScreenAction) {

    }

}