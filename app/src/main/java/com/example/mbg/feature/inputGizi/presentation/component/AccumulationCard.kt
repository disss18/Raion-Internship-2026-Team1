package com.example.mbg.feature.inputGizi.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*

@Composable
fun AccumulationCard(
    totalKalori: Double,
    totalProtein: Double,
    totalKarbo: Double,
    totalLemak: Double,
    onUploadClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header: Icon + Judul
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chart_icon),
                    contentDescription = "Icon Chart",
                    tint = FoundationGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ringkasan Gizi Hari Ini",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            // Grid 2x2 untuk 4 Kotak Gizi
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatBox(modifier = Modifier.weight(1f), title = "TOTAL KALORI", value = totalKalori.toInt().toString(), unit = "kkal")
                StatBox(modifier = Modifier.weight(1f), title = "PROTEIN", value = totalProtein.toInt().toString(), unit = "g")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatBox(modifier = Modifier.weight(1f), title = "KARBOHIDRAT", value = totalKarbo.toInt().toString(), unit = "g")
                StatBox(modifier = Modifier.weight(1f), title = "LEMAK", value = totalLemak.toInt().toString(), unit = "g")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Upload
            Button(
                onClick = onUploadClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Upload", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// Komponen kecil buat kotak
@Composable
fun StatBox(modifier: Modifier = Modifier, title: String, value: String, unit: String) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GreenLight) // Warna background kotak kecil
            .padding(16.dp)
    ) {
        Column {
            Text(text = title, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = unit, fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 2.dp))
            }
        }
    }
}