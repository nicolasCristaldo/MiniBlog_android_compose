package com.nicolascristaldo.miniblog.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.nicolascristaldo.miniblog.ui.screens.home.HomeUiState
import com.nicolascristaldo.miniblog.ui.screens.home.HomeViewModel
import com.nicolascristaldo.miniblog.ui.screens.profile.ProfileScreen
import com.nicolascristaldo.miniblog.ui.screens.splash.SplashScreen
import com.nicolascristaldo.miniblog.ui.theme.gradientBackGround

@Composable
fun MiniBlogNavHost(
    uiState: HomeUiState,
    authViewModel: AuthViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Splash.route,
        modifier = modifier
    ) {
        composable(route = AppDestinations.Splash.route) {
            SplashScreen(
                viewModel = authViewModel,
                navigate = { destination ->
                    navController.navigate(destination) {
                        popUpTo(AppDestinations.Splash.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = AppDestinations.Initial.route) {
            InitialScreen(
                navigateToLogIn = { navController.navigate(AppDestinations.LogIn.route) },
                navigateToSignUp = { navController.navigate(AppDestinations.SignUp.route) },
                modifier = Modifier
                    .fillMaxSize()
                    .gradientBackGround()
            )
        }

        composable(route = AppDestinations.LogIn.route) {
            LogInScreen(
                navigateToHome = {
                    navController.navigate(AppDestinations.Home.route) {
                        popUpTo(AppDestinations.Initial.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .gradientBackGround()
            )
        }

        composable(route = AppDestinations.SignUp.route) {
            SignUpScreen(
                navigateToVerification = { navController.navigate(AppDestinations.Verification.route) },
                modifier = Modifier
                    .fillMaxSize()
                    .gradientBackGround()
            )
        }

        composable(route = AppDestinations.Verification.route) {
            LaunchedEffect(Unit) {
                authViewModel.startResetTimer()
            }

            EmailVerificationScreen(
                viewModel = authViewModel,
                navigateToHome = {
                    navController.navigate(AppDestinations.Home.route) {
                        popUpTo(AppDestinations.Initial.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .gradientBackGround()
            )
        }

        composable(route = AppDestinations.Home.route) {
            LaunchedEffect(Unit) {
                homeViewModel.loadUser()
            }

            HomeScreen(
                uiState = uiState,
                viewModel = homeViewModel,
                navigateToUserProfile = { uid ->
                    navController.navigate(AppDestinations.Profile.createRoute(uid))
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = AppDestinations.Profile.route) { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("uid") ?: ""

            ProfileScreen(
                uid = uid,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
