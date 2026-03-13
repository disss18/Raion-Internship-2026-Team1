package com.example.mbg.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.distribution.presentation.DistributionScreen
import com.example.mbg.feature.feedback.presentation.FeedbackScreen
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.home.presentation.parent.DashboardParentScreen
import com.example.mbg.feature.home.presentation.school.DashboardSchoolScreen
import com.example.mbg.feature.school.presentation.SchoolStudentScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationStatusScreen
import com.example.mbg.feature.verificationMBG.presentation.VerifStatus

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    role: String?,
    verificationStatus: String?,
    feedbackViewModel: FeedbackViewModel
) {

    val startDestination = when (role) {

        "DAPUR_MBG" -> {
            if (verificationStatus == "approved")
                Screen.DashboardMBG.route
            else
                Screen.VerificationStatus.route
        }

        "SEKOLAH" -> Screen.DashboardSekolah.route

        "ORANG_TUA" -> Screen.DashboardOrangTua.route

        else -> Screen.Home.route
    }

    navigation(
        route = Screen.Main.route,
        startDestination = startDestination
    ) {

        /**
         * DASHBOARD MBG
         */
        composable(Screen.DashboardMBG.route) {

            DashboardMBGScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel,
                onSeeAllClick = {
                    navController.navigate(Screen.Feedback.route)
                }
            )
        }

        /**
         * FEEDBACK SCREEN
         */
        composable(Screen.Feedback.route) {

            FeedbackScreen(
                navController = navController,
                viewModel = feedbackViewModel,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        /**
         * VERIFICATION STATUS
         */
        composable(Screen.VerificationStatus.route) {

            val globalViewModel: GlobalAuthViewModel = viewModel()

            val currentStatusStr by
            globalViewModel.verificationStatus.collectAsState()

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

                            popUpTo(Screen.Main.route) {
                                inclusive = true
                            }
                        }

                    } else if (uiStatus == VerifStatus.FAILED) {

                        navController.navigate(Screen.VerificationMBG.route)
                    }
                },

                onRefreshClick = {
                    globalViewModel.updateVerificationStatus()
                }
            )
        }

        /**
         * DASHBOARD SEKOLAH
         */
        composable(Screen.DashboardSekolah.route) {

            DashboardSchoolScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel
            )
        }

        /**
         * SCREEN SISWA
         */
        composable(Screen.SchoolStudent.route) {

            SchoolStudentScreen(navController = navController)
        }

        /**
         * DASHBOARD ORANG TUA
         */
        composable(Screen.DashboardOrangTua.route) {

            DashboardParentScreen(
                navController = navController
            )
        }

        composable(Screen.Distribution.route) {

            DistributionScreen()
        }
    }
}