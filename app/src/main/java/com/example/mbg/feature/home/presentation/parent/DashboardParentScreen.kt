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

@Composable
fun DashboardParentScreen() {
    val parentBottomNav = listOf(

        BottomNavItem(
            "Beranda",
            com.example.mbg.R.drawable.beranda_botom
        ),

        BottomNavItem(
            "Menu",
            com.example.mbg.R.drawable.beranda_botom
        ),

        BottomNavItem(
            "Aktivitas",
            com.example.mbg.R.drawable.aktivitas_bottom
        ),

        BottomNavItem(
            "Profil",
            R.drawable.profil_bottom
        )
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

            /** HEADER PROFILE */
            ProfileHeaderCard()

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                /** MENU HARIAN */
                MenuDailyCard()

                Spacer(modifier = Modifier.height(16.dp))

                /** RINGKASAN GIZI */
                DashboardSectionTitle("Ringkasan Gizi Hari Ini")

                Spacer(modifier = Modifier.height(8.dp))

                NutritionSummaryGrid()

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}