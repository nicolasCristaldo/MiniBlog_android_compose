package com.nicolascristaldo.miniblog.ui.screens.auth.initial.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.theme.dark_blue

@Composable
fun LogButton(
    text: Int,
    imageRes: Int? = null,
    color: Color = Color.White,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.log_button_height))
            .padding(horizontal = dimensionResource(R.dimen.padding_extra_large))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
            .background(color = color)
            .clickable { onClick() }
    ) {
        if (imageRes != null) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = dimensionResource(R.dimen.padding_large))
                    .size(dimensionResource(R.dimen.icon_size_medium))
            )
        }
        Text(
            text = stringResource(text),
            color = dark_blue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}