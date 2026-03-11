package com.example.mbg.feature.home.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackRatingCard() {

    var rating by remember { mutableStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Bagaimana Kualitas menu hari ini?",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row {

                repeat(5) { index ->

                    IconButton(
                        onClick = { rating = index + 1 }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint =
                                if (index < rating)
                                    Color(0xFFFFC107)
                                else
                                    Color.LightGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDFF1E6)
                )
            ) {

                Text(
                    "Beri Masukan",
                    color = Color(0xFF4BA26A)
                )
            }
        }
    }
}