package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArticleCard(

    onClick: () -> Unit

) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {

        Column {

            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = "Pentingnya Protein untuk Tumbuh Kembang Anak",
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Protein adalah blok bangunan utama tubuh...",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(

                    onClick = onClick

                ) {

                    Text("Baca Artikel")

                }
            }
        }
    }
}