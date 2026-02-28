package com.example.mbg.feature.splashscreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
@Composable
fun RoleCard(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFE0F2F1) else Color.White
    val borderColor = if (isSelected) Color(0xFF4DB6AC) else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Kotak abu-abu sementara untuk tempat Ikon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// Pastikan kamu sudah import Preview di bagian atas file jika belum ada:
// import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun RoleCardPreview() {
    // Karena cetakan kita butuh bahan baku (judul, deskripsi, dll),
    // kita harus memberikan data "bohongan" (dummy) khusus untuk preview ini.
    Column(modifier = Modifier.padding(16.dp)) {

        // 1. Contoh tampilan kalau TIDAK dipilih
        RoleCard(
            title = "Contoh Peran (Belum Dipilih)",
            description = "Ini adalah contoh deskripsi dari cetakan yang belum diklik.",
            isSelected = false, // <-- Coba perhatikan ini
            onClick = {} // Kosongkan saja karena ini cuma preview
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Contoh tampilan kalau SEDANG dipilih
        RoleCard(
            title = "Contoh Peran (Sedang Dipilih)",
            description = "Warnanya berubah jadi hijau karena statusnya sedang dipilih.",
            isSelected = true, // <-- Coba perhatikan ini
            onClick = {}
        )
    }
}