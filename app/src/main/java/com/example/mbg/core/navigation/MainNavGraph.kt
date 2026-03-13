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
import com.example.mbg.feature.reward.presentation.screen.RewardScreen
import com.example.mbg.feature.school.presentation.SchoolStudentScreen
import com.example.mbg.feature.verificationMBG.presentation.VerificationStatusScreen
import com.example.mbg.feature.verificationMBG.presentation.VerifStatus

// 🔥 IMPORT LAYAR PROFIL
import com.example.mbg.feature.profile.presentation.EditProfileScreen
import com.example.mbg.feature.profile.presentation.FaqScreen
import com.example.mbg.feature.profile.presentation.ProfileScreen
import com.example.mbg.feature.profile.presentation.UserProfile
import com.example.mbg.feature.profile.presentation.UserRole

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

        composable(Screen.DashboardMBG.route) {
            DashboardMBGScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel,
                onSeeAllClick = {
                    navController.navigate(Screen.Feedback.route)
                }
            )
        }

        composable(Screen.Feedback.route) {
            FeedbackScreen(
                navController = navController,
                viewModel = feedbackViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

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
                onRefreshClick = { globalViewModel.updateVerificationStatus() }
            )
        }

        composable(Screen.DashboardSekolah.route) {
            DashboardSchoolScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel
            )
        }

        composable(Screen.SchoolStudent.route) {
            SchoolStudentScreen(navController = navController)
        }

        composable(Screen.DashboardOrangTua.route) {
            DashboardParentScreen(navController = navController)
        }

        composable(Screen.Reward.route) {
            RewardScreen(navController = navController)
        }

        composable(Screen.Distribution.route) {
            DistributionScreen()
        }

/// ==========================================
        // 🔥 FITUR PROFIL, EDIT PROFIL, DAN FAQ
        // ==========================================
        composable("profile") {
            val globalAuthViewModel: GlobalAuthViewModel = viewModel()

            val userProfileData = when (role) {
                "DAPUR_MBG" -> UserProfile(
                    role = UserRole.DAPUR,
                    name = "Marhaban",
                    subtitle = "Dapur Klojen",
                    email = "kitchen1@mbg.gov",
                    phone = "+1 (555) 987-6543"
                )
                "SEKOLAH" -> UserProfile(
                    role = UserRole.SEKOLAH,
                    name = "MAN 2 Malang",
                    subtitle = "Admin Sekolah",
                    email = "man2malang@gmail.com",
                    phone = "+62 8123 4567 8910"
                )
                else -> UserProfile(
                    role = UserRole.ORANGTUA,
                    name = "Masyithah",
                    subtitle = "Orang Tua Siswa",
                    email = "masyithah@gmail.com",
                    phone = "+62 8888 1111 9999"
                )
            }

            // 🔥 KIRIM NAVCONTROLLER DAN ROLE KE SINI
            ProfileScreen(
                navController = navController,
                roleString = role,
                userProfile = userProfileData,
                onEditClick = { navController.navigate("edit_profile") },
                onFaqClick = { navController.navigate("faq") },
                onLogoutClick = {
                    globalAuthViewModel.logout()
                }
            )
        }

        composable("edit_profile") {
            val mappedRole = when (role) {
                "DAPUR_MBG" -> UserRole.DAPUR
                "SEKOLAH" -> UserRole.SEKOLAH
                else -> UserRole.ORANGTUA
            }
            EditProfileScreen(
                role = mappedRole,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("faq") {
            FaqScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}