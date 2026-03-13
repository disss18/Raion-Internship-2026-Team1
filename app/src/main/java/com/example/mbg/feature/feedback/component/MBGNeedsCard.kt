package com.example.mbg.feature.feedback.component

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
import com.example.mbg.ui.theme.MBGBorder
import com.example.mbg.ui.theme.MBGButton
import com.example.mbg.R

@Composable
fun MBGNeedsCard(
    value: String,
    onValueChange: (String) -> Unit,
    onSave: () -> Unit
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
                    painter = painterResource(R.drawable.kebutuhan_mbg),
                    contentDescription = null,
                    tint = Color(0xFF5BA37B)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    "Kebutuhan MBG",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(10.dp))

            Text("Siswa Membutuhkan MBG")

            Spacer(Modifier.height(18.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Contoh: 450",
                    color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(

                    focusedContainerColor = Color(0xFFEDEFF3),
                    unfocusedContainerColor = Color(0xFFEDEFF3),

                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MBGButton
                )
            ) {
                Text("Simpan",
                    color = Color.White)
            }
        }
    }
}