package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackRatingCard(
    onSubmit: (rating: Int, comment: String) -> Unit
) {

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text("Bagaimana kualitas menu hari ini?")

            Spacer(Modifier.height(12.dp))

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

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                placeholder = { Text("Tulis masukan...") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {

                    if (rating > 0 && comment.isNotBlank()) {

                        onSubmit(rating, comment)

                        comment = ""
                        rating = 0
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Kirim Feedback")
            }
        }
    }
}