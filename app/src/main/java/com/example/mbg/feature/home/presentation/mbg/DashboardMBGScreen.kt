package com.example.mbg.feature.home.presentation.mbg

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.core.ui.component.*
import com.example.mbg.feature.home.presentation.component.*

@Composable
fun DashboardMBGScreen() {

    Scaffold(
        containerColor = Color.White,
        bottomBar = { DashboardBottomBar() }
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

                FeedbackCard()
            }
        }
    }
}