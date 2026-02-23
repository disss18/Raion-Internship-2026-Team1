package com.example.mbg.core.navigation

sealed class Screen(val route: String) {
    object OnboardingGraph : Screen("onboarding_graph")
    object Welcome : Screen("welcome_screen")
    object Feature : Screen("feature_screen")
}