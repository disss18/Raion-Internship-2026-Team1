package com.example.mbg.feature.home.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.ui.theme.BorderGray
import com.example.mbg.ui.theme.Graybackground
import com.example.mbg.ui.theme.GreenAccent
import com.example.mbg.ui.theme.GreenChip
import com.example.mbg.ui.theme.TextGray

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MenuDailyCard() {

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderGray),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column {

            /** FOOD IMAGE PLACEHOLDER */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color.Gray)
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(
                            "Menu Harian",
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            "Rencana Gizi Seimbang A",
                            fontSize = 12.sp,
                            color = TextGray
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {

                        Text(
                            "650",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = GreenAccent
                        )

                        Text(
                            "kcal / meal",
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    FoodChip("Nasi")
                    FoodChip("Ayam Bakar")
                    FoodChip("Sayuran Kukus")

                }
            }
        }
    }
}

@Composable
fun FoodChip(text: String) {

    Row(
        modifier = Modifier
            .background(
                Graybackground,
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        /** BULLET */
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(GreenAccent, CircleShape)
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text,
            fontSize = 12.sp
        )
    }
}