package com.example.mbg.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainNavGraph() {

    navigation(
        startDestination = Screen.Home.route,
        route = Screen.Main.route
    ) {

        composable(Screen.Home.route) {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    Text("Main Screen Placeholder")
}