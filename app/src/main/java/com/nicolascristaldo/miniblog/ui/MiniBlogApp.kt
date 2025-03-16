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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicolascristaldo.miniblog.ui.components.AppAlertDialog
import com.nicolascristaldo.miniblog.ui.navigation.AppDestinations
import com.nicolascristaldo.miniblog.ui.navigation.MiniBlogNavHost
import com.nicolascristaldo.miniblog.ui.screens.home.HomeViewModel
import com.nicolascristaldo.miniblog.ui.screens.home.components.HomeTopAppBar
import com.nicolascristaldo.miniblog.ui.screens.profile.components.ProfileTopAppBar

@Composable
fun MiniBlogApp(
    homeViewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val authUser by homeViewModel.authUser.collectAsState()
    val user by homeViewModel.user.collectAsState()
    val showLogOutDialog by homeViewModel.showLogOutDialog.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    LaunchedEffect(authUser) {
        if (authUser == null) {
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
                    onLogOut = { homeViewModel.changeLogOutDialogState(true) },
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

            if (showLogOutDialog) {
                AppAlertDialog(
                    title = "Log out",
                    content = "Are you sure you want to log out?",
                    confirmText = "Log out",
                    onConfirm = { homeViewModel.logOut() },
                    onCancel = { homeViewModel.changeLogOutDialogState(false) }
                )
            }
        }
    }
}