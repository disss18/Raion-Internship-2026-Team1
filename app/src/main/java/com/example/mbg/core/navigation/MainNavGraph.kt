package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.compose.material3.Text

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {

    navigation(
        route = Screen.Main.route,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            Text("HOME SCREEN")
        }
    }
}