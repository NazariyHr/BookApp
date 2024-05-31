package com.books.app.presentation.features.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.books.app.presentation.common.theme.BookAppTheme

@Composable
fun DetailsScreenRoot(
    navController: NavController,
    bookId: Int,
    viewModel: DetailsViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DetailsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun DetailsScreen(
    state: DetailsState,
    onAction: (DetailsScreenAction) -> Unit
) {

}

@Preview
@Composable
private fun DetailsScreenPreview() {
    BookAppTheme {
        DetailsScreen(
            state = DetailsState(),
            onAction = {}
        )
    }
}