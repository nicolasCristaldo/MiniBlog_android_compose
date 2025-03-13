package com.nicolascristaldo.miniblog.ui.navigation

sealed class AppDestinations(
    val route: String
) {
    data object Splash : AppDestinations("splash")
    data object Initial : AppDestinations("initial")
    data object LogIn : AppDestinations("login")
    data object SignUp : AppDestinations("signup")
    data object Verification : AppDestinations("verification")
    data object Home : AppDestinations("home")
    data object Profile : AppDestinations("profile/{uid}") {
        fun createRoute(uid: String) = "profile/$uid"
    }
}