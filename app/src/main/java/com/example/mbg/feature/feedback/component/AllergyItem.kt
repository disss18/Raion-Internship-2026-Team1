package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AllergyItem(
    title: String,
    totalStudent: Int
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6)),
        shape = RoundedCornerShape(12.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            Text(title)

            Spacer(modifier = Modifier.height(4.dp))

            Text("$totalStudent siswa", color = Color.Gray)
        }
    }
}