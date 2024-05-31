package com.books.app.presentation.features.loading

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.books.app.presentation.common.theme.BookAppTheme

@Composable
fun LoadingScreenRoot(
    navController: NavController,
    viewModel: LoadingViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoadingScreenScreen(
        state = state
    )
}

@Composable
private fun LoadingScreenScreen(
    state: LoadingState
) {
    Text(text = "Loading screen")
}

@Preview
@Composable
private fun LoadingScreenScreenPreview() {
    BookAppTheme {
        LoadingScreenScreen(
            state = (LoadingState())
        )
    }
}