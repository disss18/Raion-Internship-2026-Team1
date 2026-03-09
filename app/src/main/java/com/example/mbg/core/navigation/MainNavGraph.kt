package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.home.presentation.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    role: String?
) {

    val startDestination = when (role) {
        "DAPUR_MBG" -> Screen.DashboardMBG.route
        "SEKOLAH" -> Screen.DashboardSekolah.route
        "ORANG_TUA" -> Screen.DashboardOrangTua.route
        else -> Screen.DashboardMBG.route
    }

    navigation(
        route = Screen.Main.route,
        startDestination = startDestination
    ) {

        composable(Screen.DashboardMBG.route) {
            DashboardMBGScreen()
        }

        composable(Screen.DashboardSekolah.route) {
            HomeScreen(navController)
        }

        composable(Screen.DashboardOrangTua.route) {
            HomeScreen(navController)
        }
    }
}