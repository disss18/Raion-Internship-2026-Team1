package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackCard(
    schoolName: String,
    parentName: String,
    comment: String,
    rating: Int,
    time: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row {

                    AvatarCircle(schoolName)

                    Spacer(Modifier.width(8.dp))

                    Column {

                        Text(
                            schoolName,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            parentName,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF4BA26A),
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        " $rating",
                        color = Color(0xFF4BA26A)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                comment,
                color = Color.Gray
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    time,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Text(
                    "Balas",
                    color = Color(0xFF4BA26A)
                )
            }
        }
    }
}

@Composable
private fun AvatarCircle(name: String) {

    val initial =
        name.trim().firstOrNull()?.uppercase() ?: "?"

    Box(
        modifier = Modifier
            .size(36.dp)
            .background(
                Color(0xFFDFF1E6),
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = initial,
            color = Color(0xFF4BA26A)
        )
    }
}