package com.nicolascristaldo.miniblog.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color

fun Modifier.gradientBackGround(): Modifier {
    return this.background(
        brush = verticalGradient(
            colors = listOf(Color.Black, dark_blue),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )
    )
}