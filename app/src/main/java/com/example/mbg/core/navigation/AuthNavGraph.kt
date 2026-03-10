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

            // ================= LOGIN =================

            composable(Screen.Login.route) {

                LoginScreen(

                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },

                    onNavigateToForgotPassword = {
                        navController.navigate(Screen.ForgotPassword.route)
                    },

                    onLoginSuccess = {}

                )
            }

            // ================= REGISTER =================

            composable(Screen.Register.route) {

                RegisterScreen(

                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Register.route) { inclusive = true }
                        }
                    },

                    onRegisterSuccess = {
                        navController.navigate(Screen.Role.route)
                    }

                )
            }

            // ================= ROLE =================

            composable(Screen.Role.route) {

                RoleScreen(

                    onRoleSelected = { role ->

                        if (role == "DAPUR_MBG") {

                            navController.navigate(Screen.VerificationMBG.route)

                        } else {

                            navController.navigate(Screen.Main.route) {

                                popUpTo(Screen.Auth.route) {
                                    inclusive = true
                                }

                                launchSingleTop = true
                            }

                        }

                    }

                )
            }

            // ================= VERIFICATION MBG =================

            composable(Screen.VerificationMBG.route) {

                val globalAuthViewModel: GlobalAuthViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel()

                VerificationScreen(

                    onBackClick = {
                        navController.popBackStack()
                    },

                    onSubmitSuccess = {

                        // 🔵 Update status supaya RootNavGraph tidak kirim balik ke Verification
                        globalAuthViewModel.setVerificationPending()

                        navController.navigate(Screen.Main.route) {

                            popUpTo(0) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            // ================= FORGOT PASSWORD =================

            composable(Screen.ForgotPassword.route) {

                ForgotPasswordScreen(

                    onBackToLogin = {
                        navController.popBackStack()
                    }

                )
            }

            // ================= RESET PASSWORD =================

            composable(Screen.ResetPassword.route) {

                ResetPasswordScreen(

                    onBack = {
                        navController.popBackStack()
                    },

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