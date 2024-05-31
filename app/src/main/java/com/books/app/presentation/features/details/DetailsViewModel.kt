package com.books.app.presentation.features.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
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

    fun onAction(action: DetailsScreenAction) {

    }
}