package com.books.app.presentation.features.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.books.app.R
import com.books.app.presentation.common.components.ObserveAsEvent
import com.books.app.presentation.common.theme.BookAppTheme
import com.books.app.presentation.common.theme.PrimaryColor
import com.books.app.presentation.common.theme.WhiteTransparent20
import com.books.app.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun LoadingScreenRoot(
    navController: NavController,
    viewModel: LoadingViewModel =
        hiltViewModel()
) {
    LoadingScreenScreen(
        events = viewModel.events,
        navigateToLibrary = {
            navController.navigate(Screen.Library, navOptions = navOptions {
                popUpTo(Screen.Loading) { inclusive = true }
            })
        }
    )
}

@Composable
private fun LoadingScreenScreen(
    events: Flow<LoadingScreenEvent>,
    navigateToLibrary: () -> Unit
) {
    ObserveAsEvent(flow = events) { event ->
        when (event) {
            LoadingScreenEvent.GoToLibraryScreen -> {
                navigateToLibrary()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.loading_background),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.loading_background_hearts),
                    contentScale = ContentScale.FillBounds
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.loading_screen_title),
                    color = PrimaryColor,
                    fontFamily = FontFamily(Font(R.font.georgia_bolditalic)),
                    fontSize = 52.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.loading_screen_subtitle),
                    color = Color(0xCCFFFFFF),
                    fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(45.dp))
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    color = Color.White,
                    trackColor = WhiteTransparent20,
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoadingScreenScreenPreview() {
    BookAppTheme {
        LoadingScreenScreen(
            events = flowOf(),
            navigateToLibrary = {}
        )
    }
}