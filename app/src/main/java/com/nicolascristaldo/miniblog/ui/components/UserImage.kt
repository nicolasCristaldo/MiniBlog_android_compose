package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nicolascristaldo.miniblog.R

@Composable
fun UserImage(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.ic_user),
        contentDescription = stringResource(R.string.profile_image_content_description),
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_small)))
            .background(color = Color.LightGray)
    )
}