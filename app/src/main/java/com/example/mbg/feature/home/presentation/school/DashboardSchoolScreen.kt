package com.example.mbg.feature.home.presentation.school

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavConfig
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.navigation.Screen
import com.example.mbg.core.ui.component.*
import com.example.mbg.feature.feedback.component.FeedbackRatingCard
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.home.presentation.component.*

@Composable
fun DashboardSchoolScreen(navController: NavController,
                          feedbackViewModel: FeedbackViewModel) {
    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            DashboardBottomBar(
                navController = navController,
                items = BottomNavConfig.school
            )
        }
    ){ padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            /** HEADER PROFILE */
            ProfileHeaderCard()

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                /** MENU HARIAN */
                MenuDailyCard()

                Spacer(modifier = Modifier.height(16.dp))

                /** JADWAL PENERIMAAN */
                DashboardSectionTitle("Jadwal Penerimaan")

                Spacer(modifier = Modifier.height(8.dp))

                DeliveryEstimateCard()

                Spacer(modifier = Modifier.height(16.dp))

                /** PENILAIAN */
                DashboardSectionTitle("Penilaian dan Masukan")

                Spacer(modifier = Modifier.height(8.dp))

                FeedbackRatingCard { rating, comment ->

                    feedbackViewModel.sendFeedback(

                        school = "SMP 01 Menteng", // sementara hardcode dulu

                        parent = "Orang Tua Siswa",

                        comment = comment,

                        rating = rating
                    )
                }
            }
        }
    }
}