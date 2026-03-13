package com.example.mbg.feature.school.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavItem
import com.example.mbg.core.navigation.Screen
import com.example.mbg.core.ui.component.DashboardBottomBar
import com.example.mbg.feature.allergy.component.AllergyInputCard
import com.example.mbg.feature.allergy.presentation.AllergyViewModel
import com.example.mbg.feature.feedback.component.FeedbackRatingCard
import com.example.mbg.feature.feedback.presentation.FeedbackViewModel
import com.example.mbg.feature.mbg.presentation.MBGViewModel
import com.example.mbg.feature.mbgamount.component.MBGNeedsCard
import com.example.mbg.ui.theme.*

@Composable
fun SchoolStudentScreen(
    navController: NavController
) {

    /** VIEWMODEL */

    val allergyViewModel: AllergyViewModel = viewModel()
    val mbgViewModel: MBGViewModel = viewModel()
    val feedbackViewModel: FeedbackViewModel = viewModel()

    /** LOAD DATA FROM SUPABASE */

    LaunchedEffect(Unit) {
        allergyViewModel.loadAllergy()
    }

    /** UI STATE */

    var allergy by remember { mutableStateOf("") }
    var studentCount by remember { mutableStateOf("") }
    var mbgNeeds by remember { mutableStateOf("") }

    val allergyList by allergyViewModel
        .allergyList
        .collectAsStateWithLifecycle()

    val schoolBottomNav = listOf(

        BottomNavItem(
            "Beranda",
            R.drawable.beranda_botom,
            Screen.DashboardSekolah.route
        ),

        BottomNavItem(
            "Siswa",
            R.drawable.siswa_bottom,
            Screen.SchoolStudent.route
        ),

        BottomNavItem(
            "Aktivitas",
            R.drawable.distribusi_bottom,
            Screen.Feedback.route
        ),

        BottomNavItem(
            "Profil",
            R.drawable.profil_bottom,
            Screen.Role.route
        )
    )

    Scaffold(

        containerColor = MBGGreen,

        bottomBar = {

            DashboardBottomBar(
                navController = navController,
                items = schoolBottomNav
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            /** HEADER */

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {

                Text(
                    text = "Data Siswa MBG",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Kelola alergi, kebutuhan & feedback harian program MBG sekolah Anda",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }

            /** CONTENT */

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MBGBackground,
                        RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
                    )
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                /** ALLERGY INPUT */

                AllergyInputCard(
                    allergy = allergy,
                    studentCount = studentCount,
                    allergyList = allergyList,

                    onAllergyChange = { allergy = it },
                    onStudentChange = { studentCount = it },

                    onSave = {

                        if (allergy.isNotBlank() && studentCount.isNotBlank()) {

                            allergyViewModel.insertAllergy(
                                school = "SDN 01",
                                allergyName = allergy,
                                total = studentCount.toInt()
                            )

                            allergy = ""
                            studentCount = ""
                        }
                    },

                    onDelete = { }
                )

                Spacer(Modifier.height(16.dp))

                /** MBG NEED */

                MBGNeedsCard(
                    value = mbgNeeds,
                    onValueChange = { mbgNeeds = it },

                    onSave = {

                        if (mbgNeeds.isNotBlank()) {

                            mbgViewModel.insertMBGNeed(
                                school = "SDN 01",
                                total = mbgNeeds.toInt()
                            )

                            mbgNeeds = ""
                        }
                    }
                )

                Spacer(Modifier.height(16.dp))

                /** FEEDBACK */

                FeedbackRatingCard(

                    onSubmit = { rating, comment ->

                        feedbackViewModel.sendFeedback(
                            school = "SDN 01",
                            parent = "Sekolah",
                            comment = comment,
                            rating = rating
                        )
                    }
                )
            }
        }
    }
}