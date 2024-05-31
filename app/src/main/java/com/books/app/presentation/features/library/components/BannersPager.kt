package com.books.app.presentation.features.library.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.books.app.domain.model.Banner

@Composable
fun BannersPager(
    banners: List<Banner>
) {
    if (banners.isNotEmpty()) {
        val pagerState = rememberPagerState(
            pageCount = {
                banners.count() + 2
            },
            initialPage = 1
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
                if (pageIndex == 0) {
                    pagerState.scrollToPage(banners.count())
                }
                if (pageIndex == banners.count() + 1) {
                    pagerState.scrollToPage(1)
                }
            }
        }
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { pageIndex ->
                val banner = when (pageIndex) {
                    0 -> {
                        banners.last()
                    }

                    banners.count() + 1 -> {
                        banners.first()
                    }

                    else -> {
                        banners[pageIndex - 1]
                    }

                }
                BannerItem(banner, modifier = Modifier.padding(horizontal = 12.dp))
            }
            val indicatorCurrentPage by remember {
                derivedStateOf {
                    when (pagerState.currentPage) {
                        banners.count() + 1 -> 0
                        0 -> banners.count() - 1
                        else -> pagerState.currentPage - 1
                    }
                }
            }

            if (banners.isNotEmpty()) {
                BannersPageIndicator(
                    banners.count(),
                    indicatorCurrentPage,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}