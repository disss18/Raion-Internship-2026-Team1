package com.example.mbg.core.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbg.feature.auth.presentation.AuthState
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.onboarding.presentation.OnboardingScreen
import com.example.mbg.feature.role.presentation.RoleScreen
import com.example.mbg.feature.splashscreen.presentation.AnimatedSplashScreen
import com.example.mbg.feature.splashscreen.components.WelcomeScreen
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph(
    deepLinkRoute: String? = null
) {

    val navController = rememberNavController()
    val globalAuthViewModel: GlobalAuthViewModel = viewModel()

    val authState by globalAuthViewModel.authState.collectAsState()

    var splashFinished by remember { mutableStateOf(false) }

    var isResetFlow by remember { mutableStateOf(false) }

    // ================= SPLASH =================

    LaunchedEffect(Unit) {
        delay(2000)
        splashFinished = true
    }

    // ================= DEEP LINK HANDLER =================

    LaunchedEffect(deepLinkRoute, splashFinished) {

        if (!splashFinished) return@LaunchedEffect

        if (deepLinkRoute == Screen.ResetPassword.route) {

            isResetFlow = true

            navController.navigate(Screen.ResetPassword.route) {

                popUpTo(Screen.Splash.route)

                launchSingleTop = true
            }
        }
    }

    // ================= AUTH NAVIGATION =================

    LaunchedEffect(authState, splashFinished) {

        if (!splashFinished) return@LaunchedEffect
        if (authState is AuthState.Loading) return@LaunchedEffect

        if (isResetFlow) return@LaunchedEffect

        when (authState) {

            is AuthState.Authenticated -> {

                navController.navigate(Screen.Main.route) {

                    popUpTo(Screen.Splash.route) { inclusive = true }

                    launchSingleTop = true
                }
            }

            is AuthState.NeedRole -> {

                navController.navigate(Screen.Role.route) {

                    popUpTo(Screen.Splash.route) { inclusive = true }

                    launchSingleTop = true
                }
            }

            is AuthState.Unauthenticated -> {

                navController.navigate(Screen.Onboarding.route) {

                    popUpTo(Screen.Splash.route) { inclusive = true }

                    launchSingleTop = true
                }
            }

            else -> {}
        }
    }

    // ================= NAV HOST =================

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // ================= SPLASH =================

        composable(Screen.Splash.route) {
            AnimatedSplashScreen(
                onNavigateToOnboarding = {
                    // Ganti "onboarding" dengan nama rute string lo kalau beda
                    navController.navigate("onboarding") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    // Ganti "main" dengan nama rute string lo kalau beda
                    navController.navigate("main") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
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

        // ================= AUTH GRAPH =================

        authNavGraph(navController)

        // ================= ROLE =================

        composable(Screen.Role.route) {

            RoleScreen(

                onRoleSelected = {

                    navController.navigate(Screen.Main.route) {

                        popUpTo(Screen.Role.route) { inclusive = true }

                        launchSingleTop = true
                    }
                }
            )
        }

        // ================= MAIN GRAPH =================

        mainNavGraph(navController)
    }
}