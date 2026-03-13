package com.example.mbg.feature.feedback.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mbg.feature.feedback.component.FeedbackRatingCard

@Composable
fun SchoolInputScreen(

    viewModel: SchoolInputViewModel,

    schoolName: String
) {

    var allergyName by remember { mutableStateOf("") }
    var allergyTotal by remember { mutableStateOf("") }

    var mbgTotal by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        /**
         * Card Input Alergi
         */

        Card {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Alergi Siswa")

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = allergyName,
                    onValueChange = { allergyName = it },
                    label = { Text("Jenis Alergi") }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = allergyTotal,
                    onValueChange = { allergyTotal = it },
                    label = { Text("Jumlah Siswa") }
                )

                Spacer(Modifier.height(12.dp))

                Button(

                    onClick = {

                        viewModel.insertAllergy(

                            schoolName,

                            allergyName,

                            allergyTotal.toInt()
                        )

                        allergyName = ""
                        allergyTotal = ""
                    }
                ) {

                    Text("Simpan")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /**
         * Card Kebutuhan MBG
         */

        Card {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text("Kebutuhan MBG")

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = mbgTotal,
                    onValueChange = { mbgTotal = it },
                    label = { Text("Jumlah Siswa Membutuhkan MBG") }
                )

                Spacer(Modifier.height(12.dp))

                Button(

                    onClick = {

                        viewModel.insertMBGNeed(

                            schoolName,

                            mbgTotal.toInt()
                        )

                        mbgTotal = ""
                    }
                ) {

                    Text("Simpan")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        /**
         * Rating Dapur
         */

        FeedbackRatingCard(

            onSubmit = { rating, comment ->

                // reuse repository feedback
            }
        )
    }
}