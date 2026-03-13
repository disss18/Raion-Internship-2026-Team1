package com.example.mbg.feature.feedback.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mbg.feature.feedback.component.AllergyItem

@Composable
fun InputAllergyScreen(

    viewModel: AllergyViewModel,

    schoolName: String
) {

    val allergyList by
    viewModel.allergyList.collectAsStateWithLifecycle()

    var allergyName by remember { mutableStateOf("") }
    var totalStudent by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAllergy()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Input Alergi Siswa")

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = allergyName,
            onValueChange = { allergyName = it },
            label = { Text("Jenis Alergi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = totalStudent,
            onValueChange = { totalStudent = it },
            label = { Text("Jumlah Siswa") },
            keyboardOptions =
                KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {

                viewModel.insertAllergy(
                    schoolName,
                    allergyName,
                    totalStudent.toInt()
                )

                allergyName = ""
                totalStudent = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Simpan")
        }

        Spacer(Modifier.height(24.dp))

        LazyColumn {

            items(allergyList) { allergy ->

                AllergyItem(
                    allergy = allergy
                )

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}