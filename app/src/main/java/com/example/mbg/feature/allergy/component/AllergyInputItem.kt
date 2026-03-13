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
fun AllergyInputItem(
    allergy: AllergyModel,
    onDelete: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFEDEFF3),
                RoundedCornerShape(10.dp)
            )
            .padding(10.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(
                        Color(0xFF5BA37B),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = allergy.totalStudent.toString(),
                    color = Color.White
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(allergy.allergyName)
        }

        IconButton(
            onClick = onDelete
        ) {

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}