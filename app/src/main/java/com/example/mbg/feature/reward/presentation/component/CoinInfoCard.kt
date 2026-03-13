package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mbg.R

@Composable
fun CoinInfoCard() {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Row(

            verticalAlignment = Alignment.CenterVertically,

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {

            Box(

                modifier = Modifier
                    .size(34.dp)
                    .background(
                        Color(0xFFE8F1FF),
                        RoundedCornerShape(8.dp)
                    ),

                contentAlignment = Alignment.Center

            ) {

                Icon(
                    painter = painterResource(R.drawable.coin_info),
                    contentDescription = null,
                    tint = Color(0xFF3B82F6),
                    modifier = Modifier.size(18.dp)
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text("Baca Artikel Harian")

                Text(
                    "Tips Nutrisi Harian",
                    color = Color.Gray
                )

            }

            Text(
                "+5 Poin",
                color = Color(0xFF2E7D32)
            )

        }

    }

}