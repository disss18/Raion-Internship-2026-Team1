package com.example.mbg.feature.home.presentation.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.ui.component.*
import com.example.mbg.feature.home.presentation.component.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.feedback.component.FeedbackRatingCard

@Composable
fun DashboardParentScreen(
    feedbackViewModel: FeedbackViewModel = viewModel()
) {

    val parentBottomNav = listOf(
        BottomNavItem("Beranda", R.drawable.beranda_botom),
        BottomNavItem("Menu", R.drawable.beranda_botom),
        BottomNavItem("Aktivitas", R.drawable.aktivitas_bottom),
        BottomNavItem("Profil", R.drawable.profil_bottom)
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = { DashboardBottomBar(parentBottomNav) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            ProfileHeaderCard()

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                MenuDailyCard()

                Spacer(modifier = Modifier.height(16.dp))

                DashboardSectionTitle("Ringkasan Gizi Hari Ini")

                Spacer(modifier = Modifier.height(8.dp))

                NutritionSummaryGrid()

                Spacer(modifier = Modifier.height(16.dp))

                /** FEEDBACK */

                DashboardSectionTitle("Feedback Menu")

                Spacer(modifier = Modifier.height(8.dp))

                FeedbackRatingCard { rating, comment ->

                    feedbackViewModel.sendFeedback(
                        school = "SDN 1 Jakarta",
                        parent = "Orang Tua",
                        comment = comment,
                        rating = rating
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}