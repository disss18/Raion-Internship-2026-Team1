package com.example.mbg.feature.home.presentation.mbg

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.ui.component.*
import com.example.mbg.feature.feedback.component.FeedbackCard
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.home.presentation.component.*
import com.example.mbg.core.util.formatTimeAgo

@Composable
fun DashboardMBGScreen(
    feedbackViewModel: FeedbackViewModel
) {

    val uiState by feedbackViewModel.uiState.collectAsStateWithLifecycle()

    val feedbackList by remember(uiState.feedbackList) {
        mutableStateOf(uiState.feedbackList)
    }

    LaunchedEffect(Unit) {
        feedbackViewModel.refresh()
    }

    println("UI FEEDBACK SIZE = ${feedbackList.size}")

    val mbgBottomNav = listOf(
        BottomNavItem("Beranda", R.drawable.beranda_botom),
        BottomNavItem("Menu", R.drawable.menu_bottom),
        BottomNavItem("Distribusi", R.drawable.distribusi_bottom),
        BottomNavItem("Profil", R.drawable.profil_bottom)
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = { DashboardBottomBar(mbgBottomNav) }
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

                DashboardSectionTitle("Jadwal Pengiriman")

                DeliveryScheduleItem(
                    "MAN 2 MALANG",
                    "300 Makanan",
                    "10:45 WIB",
                    "BERANGKAT"
                )

                Spacer(modifier = Modifier.height(8.dp))

                DeliveryScheduleItem(
                    "MTS 2 MALANG",
                    "240 Makanan",
                    "11:00 WIB",
                    "TERJADWAL"
                )

                Spacer(modifier = Modifier.height(8.dp))

                DeliveryScheduleItem(
                    "MIN 2 MALANG",
                    "240 Makanan",
                    "11:15 WIB",
                    "TERJADWAL"
                )

                Spacer(modifier = Modifier.height(16.dp))

                DashboardSectionTitle(
                    "Feedback Terbaru",
                    "Lihat Semua"
                )

                Spacer(modifier = Modifier.height(8.dp))

                /**
                 * Jika belum ada feedback
                 */
                if (feedbackList.isEmpty()) {

                    Text(
                        text = "Belum ada feedback",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                } else {

                    feedbackList
                        .take(3)
                        .forEach { feedback ->

                            FeedbackCard(
                                schoolName = feedback.schoolName,
                                parentName = feedback.parentName,
                                comment = feedback.comment,
                                rating = feedback.rating,
                                time = feedback.createdAt?.let(::formatTimeAgo) ?: "-"
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                }
            }
        }
    }
}