package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mbg.R

@Composable
fun RewardCard(

    title: String,

    point: Int,

    userPoint: Int,

    onRedeemClick: () -> Unit

) {

    Card(

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Column(

            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.padding(16.dp)

        ) {

            Image(
                painter = painterResource(R.drawable.shope_pay),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "SHOPEEPAY",
                color = Color.Gray
            )

            Text(
                text = title
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$point Poin",
                color = Color(0xFF4C8F65)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(

                onClick = onRedeemClick,

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4C8F65)
                ),

                shape = RoundedCornerShape(10.dp)

            ) {

                Text(
                    text = "Tukar",
                    color = Color.White
                )

            }

        }

    }

}