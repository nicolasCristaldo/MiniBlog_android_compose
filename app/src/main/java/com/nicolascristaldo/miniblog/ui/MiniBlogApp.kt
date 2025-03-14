package com.nicolascristaldo.miniblog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nicolascristaldo.miniblog.ui.navigation.AppDestinations
import com.nicolascristaldo.miniblog.ui.navigation.MiniBlogNavHost
import com.nicolascristaldo.miniblog.ui.screens.home.HomeViewModel
import com.nicolascristaldo.miniblog.ui.screens.home.components.HomeTopAppBar
import com.nicolascristaldo.miniblog.ui.screens.profile.components.ProfileTopAppBar

@Composable
fun MiniBlogApp(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val user by homeViewModel.user.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val isLoggedOut by homeViewModel.isLoggedOut.collectAsState()
    val currentScreen = backStackEntry?.destination?.route

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            navController.navigate(AppDestinations.Initial.route) {
                popUpTo(AppDestinations.Home.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            when(currentScreen) {
                AppDestinations.Home.route -> HomeTopAppBar(
                    navigateToProfile = {
                        navController.navigate(AppDestinations.Profile.createRoute(user?.uid ?: ""))
                    },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                AppDestinations.Profile.route -> ProfileTopAppBar(
                    navigateBack = { navController.popBackStack() },
                    logout = { homeViewModel.logOut() },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

        },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Surface {
            MiniBlogNavHost(
                user = user,
                navController = navController,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}