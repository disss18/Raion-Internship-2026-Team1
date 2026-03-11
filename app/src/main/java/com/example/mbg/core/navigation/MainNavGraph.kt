package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.home.presentation.HomeScreen
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.home.presentation.parent.DashboardParentScreen
import com.example.mbg.feature.home.presentation.school.DashboardSchoolScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationStatusScreen
import com.example.mbg.feature.verificationMBG.presentation.VerifStatus

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    role: String?,
    verificationStatus: String?
) {

    val startDestination = when (role) {

        "DAPUR_MBG" -> {

            when (verificationStatus) {

                "pending" -> Screen.VerificationStatus.route

                "approved" -> Screen.DashboardMBG.route

                else -> Screen.VerificationStatus.route
            }
        }

        "SEKOLAH" -> Screen.DashboardSekolah.route

        "ORANG_TUA" -> Screen.DashboardOrangTua.route

        else -> Screen.DashboardMBG.route
    }

    navigation(
        route = Screen.Main.route,
        startDestination = startDestination
    ) {

        // ================= DASHBOARD MBG =================

        composable(Screen.DashboardMBG.route) {

            DashboardMBGScreen()
        }

        // ================= VERIFICATION STATUS =================

        composable(Screen.VerificationStatus.route) {

            VerificationStatusScreen(
                status = VerifStatus.PENDING
            )
        }

        // ================= DASHBOARD SEKOLAH =================

        composable(Screen.DashboardSekolah.route) {
            DashboardSchoolScreen()
        }

        // ================= DASHBOARD ORANG TUA =================

        composable(Screen.DashboardOrangTua.route) {
            DashboardParentScreen()
        }
    }
}