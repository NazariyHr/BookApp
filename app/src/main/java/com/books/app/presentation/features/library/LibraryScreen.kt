package com.books.app.presentation.features.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.books.app.R
import com.books.app.presentation.common.theme.BookAppTheme
import com.books.app.presentation.features.library.components.BannerItem
import com.books.app.presentation.features.library.components.BannersPageIndicator
import com.books.app.presentation.features.library.components.BookItem

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(16, 16, 16))
    ) {
        Text(
            text = stringResource(id = R.string.library_screen_title),
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 8.dp),
            color = Color(208, 0, 110),
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
            fontSize = 20.sp
        )
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 12.dp)
        ) {
            item {
                val pagerState = rememberPagerState(
                    pageCount = {
                        state.banners.count()
                    }
                )
                Box {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { pageIndex ->
                        val banner = state.banners[pageIndex]
                        BannerItem(banner, modifier = Modifier.padding(horizontal = 12.dp))
                    }

                    if (state.banners.isNotEmpty()) {
                        BannersPageIndicator(
                            state.banners.count(),
                            pagerState.currentPage,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 10.dp)
                        )
                    }
                }
            }

            state.books.forEach { (genre, books) ->
                item {
                    Spacer(
                        modifier = Modifier
                            .height(18.dp)
                            .padding(horizontal = 12.dp)
                    )
                }
                item {
                    Text(
                        text = genre,
                        modifier = Modifier
                            .padding(bottom = 8.dp, start = 12.dp),
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                        fontSize = 20.sp
                    )
                }
                item {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(books) { book ->
                            BookItem(book)
                        }
                    }
                }
            }
        }
    }

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