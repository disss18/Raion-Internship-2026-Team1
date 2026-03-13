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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        Color(0xFFE6F4EA),
                        CircleShape
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = allergy.totalStudent.toString()
                )
            }

            Spacer(Modifier.width(10.dp))

            Text(
                text = allergy.allergyName,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { onDelete(allergy) }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }
        }
    }
}