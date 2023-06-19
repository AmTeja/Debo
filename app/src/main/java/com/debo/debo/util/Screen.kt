package com.debo.debo.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object FeedScreen : Screen("feed_screen")
    object SearchScreen : Screen("search_screen")
    object MessagesScreen : Screen("messages_screen")
    object ProfileScreen : Screen("profile_screen")
}