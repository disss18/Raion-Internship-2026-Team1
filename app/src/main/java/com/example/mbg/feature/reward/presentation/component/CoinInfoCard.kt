package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoinInfoCard() {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text("📘")

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = "Baca Artikel Harian",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Tips Nutrisi Harian",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text("+5 Poin")
        }
    }
}