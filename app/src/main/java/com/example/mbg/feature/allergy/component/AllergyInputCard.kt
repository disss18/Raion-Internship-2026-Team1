package com.example.mbg.feature.allergy.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mbg.R
import com.example.mbg.ui.theme.MBGBorder
import com.example.mbg.ui.theme.MBGButton
import com.example.mbg.feature.allergy.domain.model.AllergyModel

@Composable
fun AllergyInputCard(
    allergy: String,
    studentCount: String,
    allergyList: List<AllergyModel>,
    onAllergyChange: (String) -> Unit,
    onStudentChange: (String) -> Unit,
    onSave: () -> Unit,
    onDelete: (Int) -> Unit
) {

    Card(
        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(4.dp),

        border = BorderStroke(
            1.dp,
            MBGBorder
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(R.drawable.alergi_logo),
                    contentDescription = null,
                    tint = Color(0xFF5BA37B)
                )

                Spacer(Modifier.width(6.dp))

                Text("Alergi Siswa")
            }

            Spacer(Modifier.height(12.dp))

            Text("Jenis Alergi")

            OutlinedTextField(
                value = allergy,
                onValueChange = onAllergyChange,
                placeholder = { Text("Masukkan jenis alergi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEDEFF3),
                    unfocusedContainerColor = Color(0xFFEDEFF3),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text("Jumlah Siswa")

            Row {

                OutlinedTextField(
                    value = studentCount,
                    onValueChange = onStudentChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("0") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFEDEFF3),
                        unfocusedContainerColor = Color(0xFFEDEFF3),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = onSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MBGButton
                    )
                ) {

                    Text(
                        "Simpan",
                        color = Color.White
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            allergyList.forEachIndexed { index, item ->

                AllergyInputItem(
                    allergy = item,
                    onDelete = { onDelete(index) }
                )

                Spacer(Modifier.height(6.dp))
            }
        }
    }
}