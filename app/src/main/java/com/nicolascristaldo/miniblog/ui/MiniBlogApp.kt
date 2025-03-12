package com.nicolascristaldo.miniblog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.nicolascristaldo.miniblog.ui.navigation.MiniBlogNavHost

@Composable
fun MiniBlogApp(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {},
        floatingActionButton = {},
        modifier = Modifier.fillMaxSize()
    ) { contentPadding ->
        Surface {
            MiniBlogNavHost(
                navController = navController,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}