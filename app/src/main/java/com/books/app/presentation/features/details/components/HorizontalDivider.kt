package com.books.app.presentation.features.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier
) {
    Spacer(
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color(217, 213, 214))
    )
}