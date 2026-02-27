package com.example.mbg.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbg.onboarding.presentation.OnboardingScreen
import com.example.mbg.splashscreen.AnimatedSplashScreen
import com.example.mbg.splashscreen.WelcomeScreen

@Composable
fun RootNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // ================= SPLASH =================
        composable(Screen.Splash.route) {
            AnimatedSplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ================= ONBOARDING =================
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ================= WELCOME =================
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // ================= NESTED GRAPH =================
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}