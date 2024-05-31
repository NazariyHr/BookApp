package com.books.app.presentation.features.loading

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    companion object {
        const val STATE_KEY = "state"
    }

    private var stateValue: LoadingState
        set(value) {
            savedStateHandle[STATE_KEY] = value
        }
        get() {
            return savedStateHandle.get<LoadingState>(STATE_KEY)!!
        }

    val state = savedStateHandle.getStateFlow(STATE_KEY, LoadingState())

    private val _events = Channel<LoadingScreenEvent>()
    val events = _events.receiveAsFlow()


}