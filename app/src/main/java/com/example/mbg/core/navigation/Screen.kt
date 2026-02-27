package com.example.mbg.core.navigation

sealed class Screen(val route: String) {
    object OnboardingGraph : Screen("onboarding_graph")
    object Welcome : Screen("welcome_screen")
    object Feature : Screen("feature_screen")
    // Root
    object Onboarding : Screen("onboarding")
    object Splash : Screen("splash")
    // Auth Graph
    object Auth : Screen("auth_graph")
    object Login : Screen("login")
    object Register : Screen("register")

    object Main : Screen("main_graph")
    object Home : Screen("home")
}