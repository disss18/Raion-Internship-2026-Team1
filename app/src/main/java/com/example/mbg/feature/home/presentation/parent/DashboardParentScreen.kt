package com.example.mbg.feature.home.presentation.parent

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.ui.component.*
import com.example.mbg.feature.home.presentation.component.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mbg.core.navigation.BottomNavConfig
import com.example.mbg.core.navigation.Screen
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.feedback.component.FeedbackRatingCard
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.reward.presentation.viewmodel.RewardViewModel
import io.github.jan.supabase.gotrue.auth

@Composable
fun DashboardParentScreen(
    feedbackViewModel: FeedbackViewModel = viewModel(),
    rewardViewModel: RewardViewModel = viewModel(),
    navController: NavController
) {

    val userId = SupabaseClientProvider.client.auth.currentUserOrNull()?.id

    /**
     * AUTO POINT SAAT BUKA DASHBOARD
     */

    LaunchedEffect(userId) {

        userId?.let {

            rewardViewModel.addDashboardPoint(it)

        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            DashboardBottomBar(
                navController = navController,
                items = BottomNavConfig.parent
            )
        }
    ){ padding ->

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

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}