package com.books.app.presentation.features.library.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BannersPageIndicator(
    totalPages: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    indicatorSize: Dp = 6.dp,
    unselectedColor: Color = Color(193f, 194f, 202f),
    selectedColor: Color = Color(208f, 0f, 110f),
    spacing: Dp = indicatorSize * 2
) {
    assert(
        value = currentPage in 0 until totalPages,
        lazyMessage = { "Current page index is out of range." }
    )
    val rowWidth =
        (indicatorSize * (totalPages - 1)) + (spacing * (totalPages - 1))
    Row(
        modifier = modifier
            .requiredWidth(rowWidth),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until totalPages) {
            val selected = i == currentPage

            val color by animateColorAsState(
                if (selected) selectedColor else unselectedColor, label = "color"
            )

            Canvas(
                modifier = Modifier.size(indicatorSize),
                onDraw = {
                    drawRoundRect(
                        color = color,
                        cornerRadius = CornerRadius(indicatorSize.toPx() / 2),
                        size = Size(indicatorSize.toPx(), indicatorSize.toPx())
                    )
                }
            )
        }
    }
}
