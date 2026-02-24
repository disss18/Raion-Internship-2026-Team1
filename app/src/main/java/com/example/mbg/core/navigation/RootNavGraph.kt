package com.example.mbg.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.mbg.onboarding.presentation.OnboardingScreen


@Composable
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onboarding_graph") {
        navigation(
            startDestination = Screen.Welcome.route,
            route = Screen.OnboardingGraph.route
        ){
            composable(Screen.Onboarding.route) {
                OnboardingScreen(
                    onFinish = {
                        navController.navigate("auth_graph") {
                            popUpTo(Screen.Onboarding.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    }
}

