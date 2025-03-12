package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.nicolascristaldo.miniblog.R

@Composable
fun UserImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = "profile image",
        modifier = modifier
            .clip(RoundedCornerShape(10))
            .background(color = Color.LightGray)
    )
}