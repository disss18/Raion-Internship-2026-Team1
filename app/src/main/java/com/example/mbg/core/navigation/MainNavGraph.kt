package com.example.mbg.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.home.presentation.HomeScreen
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationStatusScreen
import com.example.mbg.feature.verificationMBG.presentation.VerifStatus

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    role: String?,
    verificationStatus: String?
) {
    val startDestination = when (role) {
        "DAPUR_MBG" -> {
            if (verificationStatus == "approved") Screen.DashboardMBG.route
            else Screen.VerificationStatus.route
        }
        "SEKOLAH" -> Screen.DashboardSekolah.route
        "ORANG_TUA" -> Screen.DashboardOrangTua.route
        else -> Screen.VerificationStatus.route
    }

    navigation(route = Screen.Main.route, startDestination = startDestination) {
        composable(Screen.DashboardMBG.route) { DashboardMBGScreen() }

        composable(Screen.VerificationStatus.route) {
            val globalViewModel: GlobalAuthViewModel = viewModel()
            val currentStatusStr by globalViewModel.verificationStatus.collectAsState()

            val uiStatus = when (currentStatusStr) {
                "approved" -> VerifStatus.SUCCESS
                "rejected" -> VerifStatus.FAILED
                else -> VerifStatus.PENDING
            }

            VerificationStatusScreen(
                status = uiStatus,
                onNextClick = {
                    if (uiStatus == VerifStatus.SUCCESS) {
                        navController.navigate(Screen.DashboardMBG.route) {
                            popUpTo(Screen.Main.route) { inclusive = true }
                        }
                    } else if (uiStatus == VerifStatus.FAILED) {
                        navController.navigate(Screen.VerificationMBG.route)
                    }
                },
                // 🔥 TAMBAHAN: Biar user bisa refresh manual kalau lagi nunggu
                onRefreshClick = {
                    globalViewModel.updateVerificationStatus()
                }
            )
        }

        composable(Screen.DashboardSekolah.route) { HomeScreen(navController) }
        composable(Screen.DashboardOrangTua.route) { HomeScreen(navController) }
    }
}