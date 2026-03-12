package com.example.mbg.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.home.presentation.parent.DashboardParentScreen
import com.example.mbg.feature.home.presentation.school.DashboardSchoolScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationStatusScreen
import com.example.mbg.feature.verificationMBG.presentation.VerifStatus

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    feedbackViewModel: FeedbackViewModel
) {

    navigation(
        route = Screen.Main.route,
        startDestination = Screen.Home.route
    ) {

        // ROUTER
        composable(Screen.Home.route) {

            // kosong saja
        }

        composable(Screen.DashboardMBG.route) {

            DashboardMBGScreen(feedbackViewModel)
        }

        composable(Screen.VerificationStatus.route) {

            VerificationStatusScreen(status = VerifStatus.PENDING)
        }

        composable(Screen.DashboardSekolah.route) {

            DashboardSchoolScreen()
        }

        composable(Screen.DashboardOrangTua.route) {

            DashboardParentScreen()
        }
    }
}