package com.nicolascristaldo.miniblog.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.LogoImage
import com.nicolascristaldo.miniblog.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel,
    navigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val user by viewModel.userState.collectAsState()

    LaunchedEffect(user) {
        if(user != null) {
            val destination = if (user?.isEmailVerified == true) "home" else "initial"
            navigate(destination)
        }
        else {
            delay(3000)
            navigate("initial")
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        LogoImage(
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(dimensionResource(R.dimen.logo_size_splash))
        )
    }
}