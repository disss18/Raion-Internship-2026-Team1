package com.example.mbg.core.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbg.feature.auth.presentation.AuthState
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.onboarding.presentation.OnboardingScreen
import com.example.mbg.feature.splashscreen.AnimatedSplashScreen
import com.example.mbg.feature.splashscreen.WelcomeScreen
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph() {

    val navController = rememberNavController()
    val globalAuthViewModel: GlobalAuthViewModel = viewModel()
    val authState by globalAuthViewModel.authState.collectAsState()

    var splashFinished by remember { mutableStateOf(false) }

    // Splash delay 2 detik
    LaunchedEffect(Unit) {
        delay(2000)
        splashFinished = true
    }

    // Navigation hanya setelah splash selesai & auth resolved
    LaunchedEffect(authState, splashFinished) {

        if (!splashFinished) return@LaunchedEffect
        if (authState is AuthState.Loading) return@LaunchedEffect

        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(Screen.Main.route) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is AuthState.Unauthenticated -> {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }

            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            AnimatedSplashScreen()
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

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

        authNavGraph(navController)
        mainNavGraph(navController)
    }
}