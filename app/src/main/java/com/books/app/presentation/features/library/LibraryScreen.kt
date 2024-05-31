package com.books.app.presentation.features.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.books.app.presentation.common.theme.BookAppTheme

@Composable
fun LibraryScreenRoot(
    navController: NavController,
    viewModel: LibraryViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LibraryScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LibraryScreen(
    state: LibraryState,
    onAction: (LibraryScreenAction) -> Unit
) {

}

@Preview
@Composable
private fun LibraryScreenPreview() {
    BookAppTheme {
        LibraryScreen(
            state = LibraryState(),
            onAction = {}
        )
    }
}