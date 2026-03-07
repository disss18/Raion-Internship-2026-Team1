package com.example.mbg.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mbg.ui.theme.BorderGray
import com.example.mbg.ui.theme.TextGray

@Composable
fun ProfileHeaderCard() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFDFF3E8),
                        Color(0xFF5BA37B)
                    )
                ),
                shape = RoundedCornerShape(
                    bottomStart = 28.dp,
                    bottomEnd = 28.dp
                )
            )
    ) {

        Card(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .offset(y = 40.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGray),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                /** PROFILE IMAGE PLACEHOLDER */
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray, CircleShape)
                )

                Spacer(Modifier.width(12.dp))

                Column {

                    Text(
                        "Man 2 Malang",
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        "Sekolah",
                        color = TextGray,
                        fontSize = 12.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        /** EMAIL ICON PLACEHOLDER */
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = TextGray,
                            modifier = Modifier.size(14.dp)
                        )

                        Spacer(Modifier.width(4.dp))

                        Text(
                            "klojenmbg@gmail.com",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}