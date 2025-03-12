package com.nicolascristaldo.miniblog.ui.screens.home.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.miniblog.ui.components.LogoImage
import com.nicolascristaldo.miniblog.ui.components.UserImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { LogoImage(modifier = Modifier.size(50.dp)) },
        navigationIcon = { UserImage(modifier = Modifier.size(30.dp)) },
        modifier = modifier
    )
}