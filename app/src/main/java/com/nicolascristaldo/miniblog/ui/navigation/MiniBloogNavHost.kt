package com.nicolascristaldo.miniblog.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nicolascristaldo.miniblog.ui.screens.auth.AuthViewModel
import com.nicolascristaldo.miniblog.ui.screens.auth.initial.InitialScreen
import com.nicolascristaldo.miniblog.ui.screens.auth.login.LogInScreen
import com.nicolascristaldo.miniblog.ui.screens.auth.signup.SignUpScreen
import com.nicolascristaldo.miniblog.ui.screens.auth.verification.EmailVerificationScreen
import com.nicolascristaldo.miniblog.ui.screens.home.HomeScreen

@Composable
fun MiniBlogNavHost(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val user by authViewModel.userState.collectAsState()
    val startDestination = remember(user) {
        when {
            user == null -> "initial"
            user?.isEmailVerified == true -> "home"
            else -> "verification"
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
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
                navigateToHome = {
                    navController.navigate("home") {
                        popUpTo("initial") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = "signup") {
            SignUpScreen(
                navigateToVerification = { navController.navigate("verification") },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = "verification") {
            LaunchedEffect(Unit) {
                authViewModel.startResetTimer()
            }

            EmailVerificationScreen(
                viewModel = authViewModel,
                navigateToHome = {
                    navController.navigate("home") {
                        popUpTo("initial") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = "home") {
            HomeScreen(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
