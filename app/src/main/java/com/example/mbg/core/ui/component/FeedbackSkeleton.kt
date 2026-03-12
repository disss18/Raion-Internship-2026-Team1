package com.example.mbg.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackSkeleton() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.4f)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            Spacer(Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.7f)
                    .background(Color.LightGray)
            )
        }
    }
}