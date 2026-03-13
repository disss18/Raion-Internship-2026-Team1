package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.R

@Composable
fun ArticleCard(
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(4.dp)

    ) {

        Column {

            Image(

                painter = painterResource(R.drawable.ayam),

                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),

                contentScale = ContentScale.Crop

            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "Pentingnya Protein untuk Tumbuh Kembang Anak",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Protein adalah blok bangunan utama tubuh. Pelajari sumber protein untuk bekal sekolah anak.",
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(

                    onClick = onClick,

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD8E5DE)
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text(
                        text = "Baca Artikel",
                        color = Color(0xFF4C8F65)
                    )

                }

            }

        }

    }

}