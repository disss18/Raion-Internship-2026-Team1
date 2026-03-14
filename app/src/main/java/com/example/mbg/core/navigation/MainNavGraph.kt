package com.example.mbg.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.mbg.core.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth

// Feature Imports
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.feature.distribusi.data.repository.DistribusiRepositoryImpl
import com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurScreen
import com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurViewModel
import com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurViewModelFactory
import com.example.mbg.feature.distribusi.presentation.sekolah.DistribusiSekolahScreen
import com.example.mbg.feature.distribusi.presentation.sekolah.DistribusiSekolahViewModel
import com.example.mbg.feature.feedback.presentation.FeedbackScreen
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.feature.home.presentation.parent.DashboardParentScreen
import com.example.mbg.feature.home.presentation.school.DashboardSchoolScreen
import com.example.mbg.feature.reward.presentation.screen.RewardScreen
import com.example.mbg.feature.school.presentation.SchoolStudentScreen
import com.example.mbg.feature.inputGizi.presentation.InputGiziScreen
import com.example.mbg.feature.verificationMBG.presentation.*
import com.example.mbg.feature.profile.presentation.*

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
    role: String?,
    verificationStatus: String?,
    feedbackViewModel: FeedbackViewModel
) {
    navigation(route = Screen.Main.route, startDestination = Screen.DashboardMBG.route) {

        composable(Screen.DashboardMBG.route) {
            DashboardMBGScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel,
                onSeeAllClick = { navController.navigate(Screen.Feedback.route) })
        }

        composable(Screen.DashboardSekolah.route) {
            DashboardSchoolScreen(
                navController = navController,
                feedbackViewModel = feedbackViewModel
            )
        }

        composable(Screen.DashboardOrangTua.route) {
            DashboardParentScreen(navController = navController)
        }

        // ==========================================
        // RUTE MENU
        // ==========================================
        composable(Screen.Home.route) {
            val globalAuth: GlobalAuthViewModel = viewModel()
            val currentRole by globalAuth.userRole.collectAsState()

            if (currentRole == "DAPUR_MBG") {
                com.example.mbg.feature.inputGizi.presentation.InputGiziScreen(
                    viewModel = viewModel(),
                    onNavigateToFormTambahItem = { }
                )
            } else if (currentRole == "SEKOLAH") {
                DashboardSchoolScreen(
                    navController = navController,
                    feedbackViewModel = feedbackViewModel
                )
            } else {
                DashboardParentScreen(navController = navController)
            }
        }

        // ==========================================
        // RUTE DISTRIBUSI
        // ==========================================
        composable(Screen.Distribution.route) {
            val globalAuth: GlobalAuthViewModel = viewModel()
            val currentRole by globalAuth.userRole.collectAsState()

            if (currentRole == "DAPUR_MBG") {
                val dapurViewModel: com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurViewModel =
                    viewModel()
                com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurScreen(
                    viewModel = dapurViewModel,
                    navController = navController,
                    roleString = currentRole
                )
            } else {
                val sekolahViewModel: com.example.mbg.feature.distribusi.presentation.sekolah.DistribusiSekolahViewModel =
                    viewModel()
                com.example.mbg.feature.distribusi.presentation.sekolah.DistribusiSekolahScreen(
                    viewModel = sekolahViewModel,
                    navController = navController,
                    roleString = currentRole
                )
            }
        }

        composable(Screen.TrackRecordKurir.route) {
            navController.navigate(Screen.Distribution.route)
        }

        // ==========================================
        // RUTE PROFIL
        // ==========================================
        composable(Screen.Profile.route) {
            val globalAuthViewModel: GlobalAuthViewModel = viewModel()
            val currentRole by globalAuthViewModel.userRole.collectAsState()

            var dynamicEmail by remember { mutableStateOf("Memuat...") }
            var dynamicName by remember { mutableStateOf("Memuat...") }

            LaunchedEffect(Unit) {
                try {
                    val client = com.example.mbg.core.supabase.SupabaseClientProvider.client
                    val session = client.auth.currentSessionOrNull()
                    val email = session?.user?.email ?: "user@mbg.gov"
                    dynamicEmail = email
                    dynamicName = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                } catch (e: Exception) {
                    dynamicEmail = "user@mbg.gov"
                    dynamicName = "User"
                }
            }

            val userProfileData = UserProfile(
                role = if (currentRole == "DAPUR_MBG") UserRole.DAPUR else if (currentRole == "SEKOLAH") UserRole.SEKOLAH else UserRole.ORANGTUA,
                name = dynamicName,
                subtitle = if (currentRole == "DAPUR_MBG") "Mitra Dapur" else "Pengguna MBG",
                email = dynamicEmail,
                phone = "Belum diatur"
            )

            ProfileScreen(
                navController = navController,
                roleString = currentRole,
                userProfile = userProfileData,
                onEditClick = { navController.navigate(Screen.EditProfile.route) },
                onFaqClick = { navController.navigate(Screen.Faq.route) },
                onLogoutClick = {
                    globalAuthViewModel.logout()
                    navController.navigate(Screen.Welcome.route) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        // ==========================================
        // RUTE PENDUKUNG LAINNYA
        // ==========================================
        composable(Screen.EditProfile.route) {
            val globalAuth: GlobalAuthViewModel = viewModel()
            val currentRole by globalAuth.userRole.collectAsState()
            EditProfileScreen(
                role = if (currentRole == "DAPUR_MBG") UserRole.DAPUR else UserRole.SEKOLAH,
                onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Faq.route) {
            FaqScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Artikel.route) {
            com.example.mbg.feature.reward.presentation.screen.ArtikelScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Feedback.route) {
            FeedbackScreen(
                navController = navController,
                viewModel = feedbackViewModel,
                onBackClick = { navController.popBackStack() })
        }

        composable(Screen.SchoolStudent.route) {
            SchoolStudentScreen(navController = navController)
        }

        composable(Screen.Reward.route) {
            RewardScreen(navController = navController)
        }

        composable(Screen.VerificationStatus.route) {
            VerificationStatusScreen(
                status = VerifStatus.PENDING,
                onNextClick = { navController.navigate(Screen.DashboardMBG.route) },
                onRefreshClick = {})
        }

        composable(Screen.Distribution.route) {

            val globalAuth: GlobalAuthViewModel = viewModel()
            val currentRole by globalAuth.userRole.collectAsState()

            if (currentRole == "DAPUR_MBG") {

                val repository = remember {
                    com.example.mbg.feature.distribusi.data.repository.DistribusiRepositoryImpl(
                        com.example.mbg.core.supabase.SupabaseClientProvider.client
                    )
                }

                val factory = remember {
                    com.example.mbg.feature.distribusi.presentation.dapur.DistribusiDapurViewModelFactory(repository)
                }

                val viewModel: DistribusiDapurViewModel = viewModel(factory = factory)

                DistribusiDapurScreen(
                    viewModel = viewModel,
                    navController = navController,
                    roleString = currentRole
                )

            } else if (currentRole == "SEKOLAH") {

                val viewModel: DistribusiSekolahViewModel = viewModel()

                DistribusiSekolahScreen(
                    viewModel = viewModel,
                    navController = navController,
                    roleString = currentRole
                )

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Role tidak dikenali")
                }

            }
        }
    }
}