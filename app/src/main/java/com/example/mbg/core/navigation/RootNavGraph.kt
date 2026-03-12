package com.example.mbg.core.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mbg.feature.auth.presentation.AuthState
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
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
    val feedbackViewModel: FeedbackViewModel = viewModel()

    val authState by globalAuthViewModel.authState.collectAsState()
    val userRole by globalAuthViewModel.userRole.collectAsState()
    val verificationStatus by globalAuthViewModel.verificationStatus.collectAsState()

    var splashFinished by remember { mutableStateOf(false) }
    var isResetFlow by remember { mutableStateOf(false) }

    // ================= SPLASH TIMER =================

    LaunchedEffect(Unit) {
        delay(2000)
        splashFinished = true
    }

    // ================= DEEP LINK =================

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

    LaunchedEffect(authState, userRole, verificationStatus, splashFinished) {

        if (!splashFinished) return@LaunchedEffect
        if (isResetFlow) return@LaunchedEffect

        val currentRoute =
            navController.currentBackStackEntry?.destination?.route

        when (authState) {

            is AuthState.Authenticated -> {

                if (userRole == null) return@LaunchedEffect

                when (userRole) {

                    "DAPUR_MBG" -> {

                        val target =
                            if (verificationStatus == "approved")
                                Screen.DashboardMBG.route
                            else
                                Screen.VerificationStatus.route

                        if (currentRoute != target) {

                            navController.navigate(target) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }

                    "SEKOLAH" -> {

                        if (currentRoute != Screen.DashboardSekolah.route) {

                            navController.navigate(Screen.DashboardSekolah.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }

                    "ORANG_TUA" -> {

                        if (currentRoute != Screen.DashboardOrangTua.route) {

                            navController.navigate(Screen.DashboardOrangTua.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            }

            is AuthState.NeedRole -> {

                if (currentRoute != Screen.Role.route) {

                    navController.navigate(Screen.Role.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }

            is AuthState.Unauthenticated -> {

                if (currentRoute != Screen.Onboarding.route) {

                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                        launchSingleTop = true
                    }
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

        composable(Screen.Splash.route) {

            AnimatedSplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                onNavigateToMain = {}
            )
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

        mainNavGraph(
            navController = navController,
            feedbackViewModel = feedbackViewModel
        )
    }
}