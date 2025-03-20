package com.nicolascristaldo.miniblog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicolascristaldo.miniblog.R
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
    val uiState by homeViewModel.uiState.collectAsState()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route
    val profileUserId = if(currentScreen == AppDestinations.Profile.route) {
        backStackEntry?.arguments?.getString("uid")
    }
    else null

    Scaffold(
        topBar = {
            when(currentScreen) {
                AppDestinations.Home.route -> HomeTopAppBar(
                    navigateToProfile = {
                        navController.navigate(
                            AppDestinations.Profile.createRoute(uiState.user?.uid ?: "")
                        )
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
                AppDestinations.Profile.route -> ProfileTopAppBar(
                    navigateBack = navController::popBackStack,
                    onLogOut = homeViewModel::changeLogOutDialogState,
                    currentUserId = uiState.authUser?.uid ?: "",
                    profileUserId = profileUserId ?: "",
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium))
                )
            }

        },
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Surface {
            MiniBlogNavHost(
                uiState = uiState,
                navController = navController,
                modifier = Modifier.padding(contentPadding)
            )

            if (uiState.showLogOutDialog) {
                AppAlertDialog(
                    title = R.string.dialog_logout_title,
                    content = R.string.dialog_logout_content,
                    confirmText = R.string.dialog_logout_confirm,
                    onConfirm = {
                        homeViewModel.logOut()
                        navController.navigate(AppDestinations.Initial.route) {
                            popUpTo(AppDestinations.Home.route) { inclusive = true }
                        }
                    },
                    onCancel = { homeViewModel.changeLogOutDialogState(false) }
                )
            }
        }
    }
}