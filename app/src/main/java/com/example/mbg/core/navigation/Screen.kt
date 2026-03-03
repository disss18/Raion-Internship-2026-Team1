package com.example.mbg.core.navigation

sealed class Screen(val route: String) {

    // Root
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Welcome : Screen("welcome")

    // Auth
    object Auth : Screen("auth_graph")
    object Login : Screen("login")
    object Register : Screen("register")
    object Role : Screen("role")

    // Main
    object Main : Screen("main_graph")
    object Home : Screen("home")
}