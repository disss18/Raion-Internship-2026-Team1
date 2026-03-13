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
import androidx.navigation.NavController
import com.example.mbg.core.navigation.Screen
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.feedback.component.FeedbackRatingCard

@Composable
fun DashboardParentScreen(
    feedbackViewModel: FeedbackViewModel = viewModel(),
    navController: NavController
) {

    val parentBottomNav = listOf(

        BottomNavItem(
            "Beranda",
            R.drawable.beranda_botom,
            Screen.DashboardOrangTua.route
        ),

        BottomNavItem(
            "Menu",
            R.drawable.menu_bottom,
            Screen.Home.route
        ),

        BottomNavItem(
            "Aktivitas",
            R.drawable.aktivitas_bottom,
            Screen.Feedback.route
        ),

        BottomNavItem(
            "Profil",
            R.drawable.profil_bottom,
            Screen.Role.route
        )
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = { DashboardBottomBar(
            navController = navController,
            items = parentBottomNav
        ) }
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

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}