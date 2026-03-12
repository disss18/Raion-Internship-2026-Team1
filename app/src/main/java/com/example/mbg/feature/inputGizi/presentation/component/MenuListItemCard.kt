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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.feature.inputGizi.domain.model.MenuItem

@Composable
fun MenuListItemCard(
    item: MenuItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Kotak Foto Makanan
            // (Sementara pakai kotak abu-abu. Nanti kita pasang library 'Coil' buat narik foto aslinya dari URL Supabase)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE2E8F0)) // Warna abu-abu terang
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Info Nama & Deskripsi (Kiri)
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.nama_item,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Kaya Nutrisi", // Nanti bisa diganti pakai kolom deskripsi/kategori dari database
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // 3. Info Kalori & Gram (Kanan)
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.kalori.toInt()} kkal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Porsi: ${item.berat_gram.toInt()}gr",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        // Garis tipis pembatas antar item di list
        HorizontalDivider(
            color = Color(0xFFF1F5F9),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}