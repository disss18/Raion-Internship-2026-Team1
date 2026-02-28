package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.home.presentation.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {

    navigation(
        route = Screen.Main.route,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}