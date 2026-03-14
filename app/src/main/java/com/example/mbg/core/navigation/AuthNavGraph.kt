package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.auth.presentation.forgotpassword.ForgotPasswordScreen
import com.example.mbg.feature.auth.presentation.login.LoginScreen
import com.example.mbg.feature.auth.presentation.register.RegisterScreen
import com.example.mbg.feature.auth.presentation.resetpassword.ResetPasswordScreen
import com.example.mbg.feature.role.presentation.RoleScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Screen.Auth.route,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToForgotPassword = { navController.navigate(Screen.ForgotPassword.route) },
                onLoginSuccess = {}
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) { popUpTo(Screen.Register.route) { inclusive = true } }
                },
                onRegisterSuccess = { navController.navigate(Screen.Role.route) }
            )
        }

        composable(Screen.Role.route) {
            val globalAuthViewModel: GlobalAuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            RoleScreen(
                onRoleSelected = { role ->
                    globalAuthViewModel.refreshAllData()

                    when (role) {
                        "DAPUR_MBG" -> {
                            // 🔥 BYPASS: Langsung ke Dashboard, gak usah verifikasi
                            navController.navigate(Screen.DashboardMBG.route) {
                                popUpTo(Screen.Auth.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                        "SEKOLAH" -> {
                            navController.navigate(Screen.DashboardSekolah.route) {
                                popUpTo(Screen.Auth.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                        "ORANG_TUA" -> {
                            navController.navigate(Screen.DashboardOrangTua.route) {
                                popUpTo(Screen.Auth.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }
                }
            )
        }

        composable(Screen.VerificationMBG.route) {
            VerificationScreen(
                onBackClick = { navController.popBackStack() },
                onSubmitSuccess = { navController.navigate(Screen.VerificationStatus.route) }
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onBackToLogin = { navController.popBackStack() })
        }

        composable(Screen.ResetPassword.route) {
            ResetPasswordScreen(
                onBack = { navController.popBackStack() },
                onResetSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Auth.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}