package com.example.mbg.feature.home.presentation.parent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.feature.inputGizi.presentation.GiziBoxCard
import com.example.mbg.feature.inputGizi.presentation.InputGiziViewModel
import androidx.compose.material3.Scaffold
import androidx.navigation.NavController
import com.example.mbg.core.navigation.BottomNavConfig
import com.example.mbg.core.ui.component.DashboardBottomBar

@Composable
fun MenuParentScreen(
    navController: NavController,
    viewModel: InputGiziViewModel
) {

    val totalKalori by viewModel.totalKalori.collectAsState()
    val totalProtein by viewModel.totalProtein.collectAsState()
    val totalKarbo by viewModel.totalKarbo.collectAsState()
    val totalLemak by viewModel.totalLemak.collectAsState()

    Scaffold(

        bottomBar = {

            DashboardBottomBar(
                navController = navController,
                items = BottomNavConfig.parent
            )

        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Text(
                "Menu & Gizi Hari Ini",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                shape = RoundedCornerShape(16.dp)
            ) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        "Ringkasan Gizi Hari Ini",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        GiziBoxCard(
                            modifier = Modifier.weight(1f),
                            label = "TOTAL KALORI",
                            value = totalKalori.toInt().toString(),
                            unit = "kkal"
                        )

                        GiziBoxCard(
                            modifier = Modifier.weight(1f),
                            label = "PROTEIN",
                            value = totalProtein.toInt().toString(),
                            unit = "g"
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        GiziBoxCard(
                            modifier = Modifier.weight(1f),
                            label = "KARBOHIDRAT",
                            value = totalKarbo.toInt().toString(),
                            unit = "g"
                        )

                        GiziBoxCard(
                            modifier = Modifier.weight(1f),
                            label = "SERAT/LEMAK",
                            value = totalLemak.toInt().toString(),
                            unit = "g"
                        )
                    }
                }
            }
        }
    }
}