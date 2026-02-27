package com.example.mbg.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.mbg.onboarding.presentation.OnboardingScreen
import com.example.mbg.splashscreen.AnimatedSplashScreen
import com.example.mbg.splashscreen.WelcomeScreen

@Composable
fun RootNavGraph() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // ================= Splash =================
        composable(Screen.Splash.route) {
            AnimatedSplashScreen(
                onNavigateToWelcome = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ================= Onboarding =================
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ================= Welcome =================
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Auth.route)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Auth.route) {
                        launchSingleTop = true
                    }
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // ================= Auth =================
        authNavGraph(navController)

        // ================= Main =================
        mainNavGraph()
    }
}