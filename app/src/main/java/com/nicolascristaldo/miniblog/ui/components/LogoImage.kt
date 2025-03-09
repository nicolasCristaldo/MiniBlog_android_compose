package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.nicolascristaldo.miniblog.R

@Composable
fun LogoImage(
    color: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        colorFilter = ColorFilter.tint(color),
        contentDescription = null,
        modifier = modifier
    )
}