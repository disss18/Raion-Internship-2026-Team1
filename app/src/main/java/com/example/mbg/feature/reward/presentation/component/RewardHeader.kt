package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RewardHeader() {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color(0xFF4C8F65)),

        contentAlignment = Alignment.CenterStart

    ) {

        Row(

            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier.padding(start = 16.dp)

        ) {

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(

                text = "Poin & Hadiah",

                color = Color.White,

                fontSize = 18.sp

            )

        }

    }

}