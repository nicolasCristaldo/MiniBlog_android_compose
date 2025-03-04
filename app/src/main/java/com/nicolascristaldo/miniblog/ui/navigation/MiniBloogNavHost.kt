package com.nicolascristaldo.miniblog.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolascristaldo.miniblog.ui.screens.home.HomeScreen
import com.nicolascristaldo.miniblog.ui.screens.initial.InitialScreen
import com.nicolascristaldo.miniblog.ui.screens.login.LogInScreen
import com.nicolascristaldo.miniblog.ui.screens.signup.SignUpScreen

@Composable
fun MiniBlogNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "initial",
        modifier = modifier
    ) {
        composable(route = "initial") {
            InitialScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = "login") {
            LogInScreen()
        }
        composable(route = "signup") {
            SignUpScreen()
        }
        composable(route = "home") {
            HomeScreen()
        }
    }
}
