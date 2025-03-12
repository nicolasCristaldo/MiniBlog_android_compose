package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val user by viewModel.user.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "Welcome ${user?.name}"
        )
        Text(
            text = "Your email is ${user?.email}"
        )
        Text(
            text = "Your uid is ${user?.uid}"
        )
        Text(
            text = "create at ${user?.createdAt.toString()}"
        )
        Button(
            onClick = { user?.let { navigateToProfile(it.uid) } }
        ) {
            Text(text = "profile")
        }
    }
}