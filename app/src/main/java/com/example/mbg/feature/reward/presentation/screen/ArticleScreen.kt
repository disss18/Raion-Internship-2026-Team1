package com.example.mbg.feature.reward.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtikelScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artikel", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        containerColor = White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Gambar Artikel
            Image(
                painter = painterResource(id = R.drawable.ayammenuutuh),
                contentDescription = "Gambar Artikel",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pentingnya Protein untuk Tumbuh Kembang Anak",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = black,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Masa kanak-kanak merupakan periode emas pertumbuhan di mana tubuh membutuhkan asupan nutrisi yang tepat dan seimbang. Salah satu makronutrisi paling krusial adalah protein, yang sering disebut sebagai \"blok pembangun\" bagi tubuh manusia.",
                fontSize = 14.sp,
                color = TextGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mengapa Protein Penting untuk Anak",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Protein tidak hanya sekadar memberikan energi, tetapi juga memiliki peran spesifik dalam mendukung metabolisme dan regenerasi sel tubuh yang sedang berkembang pesat.",
                fontSize = 14.sp,
                color = TextGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Kotak Highlight (Quote Hijau)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GreenLight, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "\"Protein membantu pembentukan otot, memperkuat sistem imun, dan mendukung perkembangan otak anak.\"",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                    color = GreenPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sumber Protein yang Baik",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pastikan anak mendapatkan variasi protein hewani dan nabati berikut:",
                fontSize = 14.sp,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            BulletPoint("Telur:", "Sumber protein lengkap dengan kolin untuk otak.")
            BulletPoint("Ayam & Ikan:", "Kaya asam amino esensial dan lemak sehat.")
            BulletPoint("Tahu & Tempe:", "Alternatif nabati yang kaya serat dan kalsium.")
            BulletPoint("Susu:", "Mendukung kepadatan tulang dan asupan kalsium.")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contoh Menu Bergizi untuk Anak Sekolah",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = GreenPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Memasukkan protein ke dalam bekal sekolah bisa dilakukan dengan cara yang kreatif seperti sandwich telur, nugget ayam rumahan, atau tumis tempe kecap yang disukai anak.",
                fontSize = 14.sp,
                color = TextGray,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BulletPoint(title: String, desc: String) {
    Row(modifier = Modifier.padding(bottom = 4.dp)) {
        Text("• ", fontSize = 14.sp, color = TextGray)
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextGray)) {
                    append("$title ")
                }
                append(desc)
            },
            fontSize = 14.sp,
            color = TextGray
        )
    }
}