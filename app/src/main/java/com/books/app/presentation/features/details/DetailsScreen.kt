package com.books.app.presentation.features.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.books.app.R
import com.books.app.presentation.common.theme.BookAppTheme
import com.books.app.presentation.features.details.components.HorizontalDivider
import com.books.app.presentation.features.details.components.StatsItem
import com.books.app.presentation.features.library.components.BookItem
import kotlinx.coroutines.Dispatchers
import kotlin.math.absoluteValue

@Composable
fun DetailsScreenRoot(
    navController: NavController,
    bookId: Int,
    viewModel: DetailsViewModel =
        hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.saveBookId(bookId)
    }
    DetailsScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackPressed = {
            navController.navigateUp()
        }
    )
}

@Composable
private fun DetailsScreen(
    state: DetailsState,
    onAction: (DetailsScreenAction) -> Unit,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(83, 36, 84))
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp)
                    .clickable {
                        onBackPressed()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                if (state.books.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))

                    val pagerState = rememberPagerState(
                        pageCount = { state.books.count() },
                        initialPage = state.books.indexOfFirst { it.id == state.bookIdWhileInit }
                    )
                    LaunchedEffect(pagerState, state.books) {
                        snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
                            state.books.getOrNull(pageIndex)?.let { book ->
                                onAction(DetailsScreenAction.OnBookChanged(book.id))
                            }
                        }
                    }
                    val density = LocalDensity.current
                    var pagerSize by remember { mutableStateOf(IntSize.Zero) }
                    val pagerPadding by remember {
                        derivedStateOf {
                            with(density) {
                                if (pagerSize.width == 0) {
                                    0.toDp()
                                } else {
                                    val onePageSize = with(this) { 200.dp.toPx() }
                                    val extraSpace = pagerSize.width - onePageSize
                                    val extraSpaceForOneEnd = extraSpace / 2
                                    extraSpaceForOneEnd.toDp()
                                }
                            }
                        }
                    }

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onSizeChanged {
                                pagerSize = it
                            },
                        pageSize = PageSize.Fixed(200.dp),
                        contentPadding = PaddingValues(end = pagerPadding, start = pagerPadding),
                        pageSpacing = 16.dp
                    ) { pageIndex ->
                        val book = state.books.getOrNull(pageIndex) ?: return@HorizontalPager
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    val pageOffset =
                                        ((pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction).absoluteValue
                                    alpha = lerp(
                                        start = 0.3f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )

                                    val maxHeight = 250f
                                    val minHeight = 200f
                                    val maxWidth = 200f
                                    val minWidth = 160f

                                    scaleY = lerp(
                                        start = minHeight / maxHeight,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                    scaleY = lerp(
                                        start = minWidth / maxWidth,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .decoderFactory(GifDecoder.Factory())
                                    .dispatcher(Dispatchers.IO)
                                    .memoryCacheKey(book.imageUrl)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .data(book.imageUrl)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(250.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .align(Alignment.Center),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
            item {
                state.selectedBook?.let { book ->
                    Column(
                        modifier = Modifier
                            .padding(bottom = 18.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = book.name,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = book.name,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = Color(255f, 255f, 255f, 0.8f),
                            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                            fontSize = 14.sp
                        )
                    }

                }
            }
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    ),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    state.selectedBook?.let { book ->
                        Column(
                            modifier = Modifier
                                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 22.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                StatsItem(
                                    name = stringResource(id = R.string.details_screen_readers),
                                    statInfo = book.views,
                                )
                                StatsItem(
                                    name = stringResource(id = R.string.details_screen_likes),
                                    statInfo = book.likes
                                )
                                StatsItem(
                                    name = stringResource(id = R.string.details_screen_quotes),
                                    statInfo = book.quotes
                                )
                                StatsItem(
                                    name = stringResource(id = R.string.details_screen_genre),
                                    statInfo = book.genre
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    top = 10.dp,
                                    bottom = 16.dp
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.details_screen_summary),
                                color = Color(11, 8, 15),
                                fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                                fontSize = 20.sp
                            )
                            Text(
                                text = book.summary,
                                modifier = Modifier.padding(top = 8.dp),
                                color = Color(57, 54, 55),
                                fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                                fontSize = 14.sp
                            )
                            HorizontalDivider(
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    bottom = 16.dp
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.details_screen_also_like),
                                color = Color(11, 8, 15),
                                fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                                fontSize = 20.sp
                            )
                            LazyRow(
                                modifier = Modifier.padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.booksWillAlsoLike) { book ->
                                    BookItem(
                                        book = book,
                                        onBookClicked = {},
                                        bookTitleColor = Color(57, 54, 55)
                                    )
                                }
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        bottom = 24.dp,
                                        start = 32.dp,
                                        end = 32.dp
                                    ),
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        221,
                                        72,
                                        161
                                    )
                                ),
                                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.details_screen_read_now),
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                                    fontSize = 16.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsScreenPreview() {
    BookAppTheme {
        DetailsScreen(
            state = DetailsState(),
            onAction = {},
            onBackPressed = {}
        )
    }
}