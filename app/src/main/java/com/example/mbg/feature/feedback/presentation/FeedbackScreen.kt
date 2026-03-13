package com.example.mbg.feature.feedback.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.navigation.Screen
import com.example.mbg.core.ui.component.DashboardBottomBar
import com.example.mbg.core.util.formatTimeAgo
import com.example.mbg.feature.feedback.component.*
import com.example.mbg.feature.feedback.domain.model.AllergyModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    navController: NavController,
    viewModel: FeedbackViewModel,
    onBackClick: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    /**
     * Dummy Allergy Summary
     * (sementara sebelum dari Supabase)
     */
    val allergyList = listOf(

        AllergyModel(
            schoolName = "SDN 01",
            allergyName = "Alergi Kacang",
            totalStudent = 6
        ),

        AllergyModel(
            schoolName = "SDN 01",
            allergyName = "Alergi Telur",
            totalStudent = 5
        ),

        AllergyModel(
            schoolName = "SDN 01",
            allergyName = "Vegetarian",
            totalStudent = 2
        ),

        AllergyModel(
            schoolName = "SDN 01",
            allergyName = "Alergi Ayam",
            totalStudent = 1
        )
    )

    val mbgBottomNav = listOf(

        BottomNavItem(
            "Beranda",
            R.drawable.beranda_botom,
            Screen.DashboardMBG.route
        ),

        BottomNavItem(
            "Menu",
            R.drawable.menu_bottom,
            Screen.Home.route
        ),

        BottomNavItem(
            "Distribusi",
            R.drawable.distribusi_bottom,
            Screen.Distribution.route
        ),

        BottomNavItem(
            "Profil",
            R.drawable.profil_bottom,
            Screen.Role.route
        )
    )

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Scaffold(
        containerColor = Color(0xFF5BA37B),

        topBar = {

            TopAppBar(
                title = {
                    Text("Feedback & Alergi")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF5BA37B),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        bottomBar = {
            DashboardBottomBar(
                navController = navController,
                items = mbgBottomNav
            )
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    Color(0xFFF3F4F6),
                    shape = RoundedCornerShape(
                        topStart = 28.dp,
                        topEnd = 28.dp
                    )
                )
        ) {

            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                /**
                 * Rating Summary
                 */
                item {

                    RatingSummaryCard(
                        ratingAverage = state.ratingAverage,
                        totalReview = state.totalReview,
                        ratingDistribution = state.ratingDistribution
                    )
                }

                /**
                 * Card Daftar Alergi
                 */
                item {

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Column {

                                Text(
                                    text = "Daftar Alergi siswa",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Spacer(Modifier.height(8.dp))

                                Divider(
                                    color = Color(0xFFE6E6E6),
                                    thickness = 1.dp
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                /**
                                 * Grid 2 Column
                                 */
                                allergyList.chunked(2).forEach { row ->

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {

                                        row.forEach { allergy ->

                                            Box(
                                                modifier = Modifier.weight(1f)
                                            ) {

                                                AllergySummaryItem(
                                                    allergy = allergy
                                                )
                                            }
                                        }

                                        if (row.size == 1) {
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                /**
                 * Title Feedback
                 */
                item {

                    Text(
                        text = "Feedback Terbaru",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                /**
                 * List Feedback
                 */
                items(
                    items = state.feedbackList,
                    key = { it.id ?: it.createdAt ?: "" }
                ) { feedback ->

                    FeedbackCard(
                        schoolName = feedback.schoolName,
                        parentName = feedback.parentName,
                        comment = feedback.comment,
                        rating = feedback.rating,
                        time = feedback.createdAt?.let(::formatTimeAgo) ?: "-"
                    )
                }
            }
        }
    }
}