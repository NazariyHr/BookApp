package com.books.app.presentation.features.library.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.books.app.domain.model.Banner
import kotlinx.coroutines.Dispatchers

@Composable
fun BannerItem(
    banner: Banner,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .decoderFactory(GifDecoder.Factory())
                .dispatcher(Dispatchers.IO)
                .memoryCacheKey(banner.imageUrl)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .data(banner.imageUrl)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun BannerItemPreview() {
    BannerItem(Banner(0, 2, "https://unsplash.it/600/300"))
}