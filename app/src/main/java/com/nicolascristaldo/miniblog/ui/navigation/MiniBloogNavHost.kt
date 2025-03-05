package com.nicolascristaldo.miniblog.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolascristaldo.miniblog.ui.screens.auth.initial.InitialScreen
import com.nicolascristaldo.miniblog.ui.screens.auth.login.LogInScreen
import com.nicolascristaldo.miniblog.ui.screens.auth.signup.SignUpScreen
import com.nicolascristaldo.miniblog.ui.screens.home.HomeScreen

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
                navigateToLogIn = { navController.navigate("login") },
                navigateToSignUp = { navController.navigate("signup") },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = "login") {
            LogInScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = "signup") {
            SignUpScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = "home") {
            HomeScreen()
        }
    }
}
