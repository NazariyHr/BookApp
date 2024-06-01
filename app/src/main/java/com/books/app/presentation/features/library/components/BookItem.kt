package com.books.app.presentation.features.library.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.books.app.R
import com.books.app.domain.model.Book
import kotlinx.coroutines.Dispatchers

@Composable
fun BookItem(
    book: Book,
    onBookClicked: (BookId) -> Unit,
    bookTitleColor: Color = Color(255f, 255f, 255f, 0.7f),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier.clickable { onBookClicked(book.id) }
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
                .height(150.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = book.name,
            modifier = Modifier
                .width(120.dp)
                .padding(top = 4.dp),
            color = bookTitleColor,
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview
@Composable
fun BookItemPreview() {

}