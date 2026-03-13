package com.example.mbg.feature.feedback.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.feature.feedback.domain.model.AllergyModel

@Composable
fun AllergySummaryItem(
    allergy: AllergyModel
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFE7F3EC),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {

        Column {

            Text(
                text = allergy.allergyName,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${allergy.totalStudent} siswa",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}