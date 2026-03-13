package com.example.mbg.feature.allergy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.feature.allergy.domain.model.AllergyModel

@Composable
fun AllergyItem(

    allergy: AllergyModel,

    onDelete: (AllergyModel) -> Unit = {}

) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),

        shape = RoundedCornerShape(12.dp),

        elevation = CardDefaults.cardElevation(2.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE7F3EC)
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {

                Text(
                    text = allergy.allergyName,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${allergy.totalStudent} siswa",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}