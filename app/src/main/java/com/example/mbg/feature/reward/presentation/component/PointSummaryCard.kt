package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mbg.R

@Composable
fun PointSummaryCard(

    totalPoint: Int,

    role: String

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF2F2F2)
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            /**
             * USER ICON
             */
            Box(

                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF4C8F65),
                        shape = CircleShape
                    ),

                contentAlignment = Alignment.Center

            ) {

                Image(
                    painter = painterResource(R.drawable.pfp),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(

                text = "Total Poin Saya",

                style = MaterialTheme.typography.bodyMedium,

                color = Color.Gray

            )

            Spacer(modifier = Modifier.height(6.dp))

            /**
             * POINT VALUE
             */
            Row(

                verticalAlignment = Alignment.CenterVertically,

                horizontalArrangement = Arrangement.Center

            ) {

                Image(
                    painter = painterResource(R.drawable.reward_coin),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(

                    text = totalPoint.toString(),

                    style = MaterialTheme.typography.headlineSmall,

                    fontWeight = FontWeight.Bold

                )

            }

            Spacer(modifier = Modifier.height(10.dp))

            /**
             * ROLE BADGE
             */
            Box(

                modifier = Modifier
                    .background(
                        color = Color(0xFFDCEFE4),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 4.dp
                    )

            ) {

                Text(

                    text = role,

                    color = Color(0xFF2E7D32),

                    style = MaterialTheme.typography.labelMedium,

                    fontWeight = FontWeight.Medium

                )

            }

        }

    }

}