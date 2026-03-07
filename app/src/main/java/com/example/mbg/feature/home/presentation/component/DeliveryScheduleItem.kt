package com.example.mbg.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DeliveryScheduleItem(
    school: String,
    meals: String,
    time: String,
    status: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {

        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row {

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            Color(0xFFDFF1E6),
                            RoundedCornerShape(8.dp)
                        )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {

                    Text(
                        school,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        meals,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Column {

                Text(
                    time,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    status,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (status == "BERANGKAT")
                        Color(0xFF4BA26A)
                    else
                        Color.Gray
                )
            }
        }
    }
}