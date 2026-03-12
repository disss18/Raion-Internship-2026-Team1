package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBarRow(
    star: Int,
    count: Int,
    max: Int
) {

    val progress =
        if (max == 0) 0f
        else count.toFloat() / max

    Row {

        Row(
            modifier = Modifier.width(40.dp)
        ) {

            Text("$star")

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFF4BA26A),
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .height(6.dp)
                .weight(1f)
                .background(
                    Color(0xFFE5E7EB),
                    RoundedCornerShape(10.dp)
                )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        Color(0xFF4BA26A),
                        RoundedCornerShape(10.dp)
                    )
            )
        }
    }
}