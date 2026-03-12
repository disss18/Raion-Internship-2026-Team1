package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun RatingSummaryCard(
    ratingAverage: Double,
    totalReview: Int,
    ratingDistribution: Map<Int, Int>
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(0xFFF3F4F6),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = String.format("%.1f", ratingAverage),
                        style = MaterialTheme.typography.headlineLarge
                    )

                    Spacer(Modifier.height(4.dp))

                    Row {

                        repeat(5) {

                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFF4BA26A)
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Text(
                        "$totalReview ulasan",
                        color = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            ratingDistribution
                .toSortedMap(reverseOrder())
                .forEach { (star, count) ->

                    RatingBarRow(
                        star = star,
                        count = count,
                        max = ratingDistribution.values.maxOrNull() ?: 1
                    )

                    Spacer(Modifier.height(6.dp))
                }
        }
    }
}