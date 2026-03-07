package com.example.mbg.feature.home.presentation.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.ui.theme.GreenAccent
import com.example.mbg.ui.theme.TextGray
import com.example.mbg.R

@Composable
fun FeedbackCard() {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // HEADER
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row {

                    // PROFILE PLACEHOLDER
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                Color(0xFFDFF1E6),
                                CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {

                        Text("SMP 01 MENTENG")

                        Text(
                            "Bapak Budi",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // RATING
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GreenAccent,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(Modifier.width(2.dp))

                    Text(
                        "3.5",
                        color = GreenAccent
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // FEEDBACK TEXT
            Text(
                "Anak saya sangat senang dengan menu nasi ayam bakar hari ini. Porsinya pas dan sayurnya segar. Terimakasih tim MBG!",
                color = TextGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // DIVIDER
            Divider(
                color = Color(0xFFE2E8F0),
                thickness = 0.7.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // FOOTER
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "2 jam yang lalu",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "Balas",
                        color = GreenAccent,
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(R.drawable.arrow_feedback),
                        contentDescription = null,
                        tint = GreenAccent,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}