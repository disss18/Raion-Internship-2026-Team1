package com.example.mbg.feature.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutritionSummaryGrid() {

    Column {

        Row {

            NutritionItem(
                title = "TOTAL KALORI",
                value = "650 kcal",
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            NutritionItem(
                title = "PROTEIN",
                value = "65 g",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {

            NutritionItem(
                title = "KARBOHIDRAT",
                value = "250 g",
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            NutritionItem(
                title = "VITAMIN A & C",
                value = "150 mg",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NutritionItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F6F5)
        )
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Text(
                title,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                value,
                fontSize = 16.sp
            )
        }
    }
}