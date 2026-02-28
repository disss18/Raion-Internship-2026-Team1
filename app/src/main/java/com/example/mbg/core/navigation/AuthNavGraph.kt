package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.auth.presentation.login.LoginScreen
import com.example.mbg.feature.auth.presentation.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {

    navigation(
        route = Screen.Auth.route,
        startDestination = Screen.Login.route
    ) {

        // ================= LOGIN =================
        composable(Screen.Login.route) {

            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },

                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {

                        popUpTo(0) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        // ================= REGISTER =================
        composable(Screen.Register.route) {

            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },

                // REGISTER → LOGIN (BUKAN MAIN)
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}