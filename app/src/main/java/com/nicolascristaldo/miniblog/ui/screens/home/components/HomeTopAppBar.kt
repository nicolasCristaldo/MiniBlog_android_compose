package com.nicolascristaldo.miniblog.ui.screens.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.LogoImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            LogoImage(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensionResource(R.dimen.logo_size_top_bar))
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { navigateToProfile() },
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.account_icon_content_description),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(dimensionResource(R.dimen.icon_size_large))
                )
            }
        },
        modifier = modifier
    )
}