package com.example.mbg.feature.profile.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FaqScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("FAQ", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        containerColor = BackgroundGray
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState())
        ) {
            // Background Hijau Melengkung
            Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(GreenPrimary, RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)))

            Column(modifier = Modifier.padding(horizontal = 20.dp).offset(y = (-20).dp)) {

                // SEARCH BAR
                TextField(
                    value = searchQuery, onValueChange = { searchQuery = it },
                    placeholder = { Text("Cari Pertanyaan Anda di sini...", color = TextGray, fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = White, unfocusedContainerColor = White,
                        focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp), singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                // PERTANYAAN UMUM
                Text("PERTANYAAN UMUM", fontWeight = FontWeight.Bold, color = GreenPrimary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                FaqAccordionItem(icon = R.drawable.tiles_icon, title = "Apa itu aplikasi Monitoring Gizi MBG?", content = "Aplikasi ini adalah platform terintegrasi untuk memantau status gizi siswa, mengelola distribusi makanan (Makan Bergizi Gratis), serta menghubungkan Dapur MBG, Sekolah, dan Orang Tua dalam satu ekosistem digital.")
                FaqAccordionItem(icon = R.drawable.sekelompok_orang_icon, title = "Siapa saja yang bisa menggunakan aplikasi ini?", content = "Terdapat 3 role utama dengan akses berbeda:\n1. Dapur MBG: Mengelola produksi, gizi makanan, dan verifikasi.\n2. Sekolah: Menginput data kebutuhan siswa dan memberikan feedback.\n3. Orang Tua: Memantau asupan gizi anak dan mendapatkan edukasi kesehatan.")

                Spacer(modifier = Modifier.height(16.dp))

                // ROLE DAPUR
                Text("FAQ FITUR: ROLE DAPUR MBG", fontWeight = FontWeight.Bold, color = GreenPrimary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                FaqAccordionItem(icon = R.drawable.perisai_ceklis_icon, title = "Bagaimana Cara Kerja Dapur MBG Verification System?", content = "Fitur ini digunakan untuk memverifikasi bahwa dapur telah memenuhi standar operasional sebelum memulai distribusi. Dapur wajib melakukan input data harian untuk memastikan kualitas makanan terjaga secara konsisten.")
                FaqAccordionItem(icon = R.drawable.riwayat_icon, title = "Dimana saya bisa melihat riwayat pengiriman makanan?", content = "Anda dapat mengaksesnya melalui menu Track Record MBG. Di sana terdapat log harian lengkap mengenai menu yang telah dikirimkan ke setiap sekolah mitra.")
                FaqAccordionItem(icon = R.drawable.container_exclamationmark_icon, title = "Bagaimana cara mengetahui jika ada siswa yang memiliki alergi?", content = "Hasil input dari pihak Sekolah akan muncul secara otomatis di dashboard Dapur MBG pada bagian Result Alergi & Kebutuhan. Ini memastikan tim dapur tidak salah dalam menyiapkan menu khusus bagi siswa tersebut.")

                Spacer(modifier = Modifier.height(16.dp))

                // ROLE SEKOLAH
                Text("FAQ FITUR: ROLE SEKOLAH", fontWeight = FontWeight.Bold, color = GreenPrimary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                FaqAccordionItem(icon = R.drawable.warning_icon, title = "Bagaimana cara melaporkan alergi siswa?", content = "Pihak Sekolah dapat masuk ke fitur Input Alergi & Kebutuhan MBG. Data yang Anda masukkan akan langsung terintegrasi ke sistem Dapur MBG sebagai panduan produksi makanan harian.")
                FaqAccordionItem(icon = R.drawable.ask_withpencil_icon, title = "Apakah sekolah bisa memberikan penilaian terhadap makanan?", content = "Ya, sekolah wajib mengisi fitur Feedback & Rating MBG setelah makanan diterima. Penilaian ini sangat penting untuk membantu Dapur MBG meningkatkan kualitas layanan dan rasa makanan.")

                Spacer(modifier = Modifier.height(16.dp))

                // ROLE ORANG TUA
                Text("FAQ FITUR: ROLE ORANGTUA", fontWeight = FontWeight.Bold, color = GreenPrimary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                FaqAccordionItem(icon = R.drawable.unsimetricaltiles_icon, title = "Informasi apa yang didapat di Dashboard Monitoring Gizi?", content = "Orang tua dapat melihat detail menu yang dikonsumsi anak di sekolah beserta kandungan gizinya secara transparan (seperti jumlah kalori, protein, lemak, dll) setiap harinya.")
                FaqAccordionItem(icon = R.drawable.reward_icon, title = "Apa itu sistem Rewards & Redeem?", content = "Orang tua akan mendapatkan poin setiap kali membaca Edukasi Artikel atau aktif mengecek Dashboard Gizi. Poin yang terkumpul dapat ditukarkan dengan berbagai hadiah menarik di menu Redeem.")

                Spacer(modifier = Modifier.height(24.dp))

                // KARTU HUBUNGI KAMI
                Card(
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White), border = BorderStroke(1.dp, BorderGray)
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Belum Menemukan Jawaban?", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Tim kami siap membantu anda 24/7", fontSize = 12.sp, color = TextGray)
                        Spacer(modifier = Modifier.height(16.dp))
                        PrimaryButton("Hubungi Kami", onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/6281234567890"))
                            context.startActivity(intent)
                        }, containerColor = GreenPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun FaqAccordionItem(icon: Int, title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp).clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, if (expanded) GreenPrimary else BorderGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(32.dp).background(Graybackground, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                    Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = black, modifier = Modifier.weight(1f))
                Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null, tint = TextGray)
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = BorderGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = content, fontSize = 12.sp, color = TextGray, lineHeight = 18.sp)
                }
            }
        }
    }
}