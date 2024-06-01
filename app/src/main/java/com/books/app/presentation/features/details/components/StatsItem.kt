package com.books.app.presentation.features.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.books.app.R

@Composable
fun StatsItem(
    name: String,
    statInfo: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = statInfo,
            color = Color(11, 8, 15),
            fontFamily = FontFamily(Font(R.font.nunito_sans_extra_bold)),
            fontSize = 18.sp
        )
        Text(
            text = name,
            color = Color(217, 213, 214),
            fontFamily = FontFamily(Font(R.font.nunito_sans_bold)),
            fontSize = 12.sp
        )
    }
}