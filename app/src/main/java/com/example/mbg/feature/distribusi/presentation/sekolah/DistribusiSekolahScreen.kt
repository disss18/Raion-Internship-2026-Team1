package com.example.mbg.feature.distribusi.presentation.sekolah

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavConfig
import com.example.mbg.core.ui.component.DashboardBottomBar
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistribusiSekolahScreen(
    viewModel: DistribusiSekolahViewModel,
    navController: NavController, // 🔥 Tambahan buat Navbar
    roleString: String?           // 🔥 Tambahan buat Navbar
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val data = state.data
    val status = data.status

    val bottomNavItems = when (roleString) {
        "DAPUR_MBG" -> BottomNavConfig.mbg
        "SEKOLAH" -> BottomNavConfig.school
        else -> BottomNavConfig.parent
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Status Pengiriman Makanan", color = White, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        bottomBar = {
            DashboardBottomBar(navController = navController, items = bottomNavItems)
        },
        containerColor = BackgroundGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else {
                val lat = "-7.9604"
                val lon = "112.6186"
                val mapUrl = "https://maps.googleapis.com/maps/api/staticmap?center=$lat,$lon&zoom=14&size=600x300&maptype=roadmap&markers=color:green%7C$lat,$lon&key=YOUR_API_KEY"

                when (status) {
                    "diproses" -> UIStatusDiproses(data, mapUrl)
                    "dikirim" -> UIStatusDikirim(data, context, mapUrl)
                    "diterima" -> UIStatusDiterima(data, mapUrl)
                    else -> UIStatusDiproses(data, mapUrl)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ==========================================
// 1. UI KETIKA STATUS: DIPROSES
// ==========================================
@Composable
fun UIStatusDiproses(data: com.example.mbg.feature.distribusi.data.model.DistribusiModel, mapUrl: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                StatusBadge("SEDANG DIPERSIAPKAN", Error700)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "${data.porsi}", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Porsi", color = black, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Asal Dapur", color = TextGray, fontSize = 12.sp)
            Text("Dapur MBG Klojen", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)

            Spacer(modifier = Modifier.height(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(GreenPrimary, CircleShape))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Jl. Simpang Panji Suroso", color = TextGray, fontSize = 14.sp)
                }
                Column(modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 4.dp)) {
                    repeat(3) {
                        Box(modifier = Modifier.size(2.dp).background(GreenPrimary))
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(10.dp).background(GreenPrimary, CircleShape))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("MAN 2 Kota Malang", color = black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
    AsyncImage(
        model = mapUrl,
        contentDescription = "Peta", contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)).background(BorderGray)
    )

    Spacer(modifier = Modifier.height(16.dp))
    Text("Status Pengiriman", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
    Spacer(modifier = Modifier.height(16.dp))

    TimelineItem("Pesanan Diproses", data.waktuDiproses ?: "08.30 WIB", state = "DONE", isLast = false)
    TimelineItem("Kurir Akan Berangkat", data.waktuBerangkat ?: "11.10 WIB", state = "CURRENT", isLast = true)
}

// ==========================================
// 2. UI KETIKA STATUS: DIKIRIM
// ==========================================
@Composable
fun UIStatusDikirim(data: com.example.mbg.feature.distribusi.data.model.DistribusiModel, context: android.content.Context, mapUrl: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = mapUrl,
            contentDescription = "Peta", contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(260.dp).clip(RoundedCornerShape(12.dp)).background(BorderGray)
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().padding(top = 220.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("ESTIMASI TIBA", color = TextGray, fontSize = 12.sp)
                    StatusBadge("SEDANG DIKIRIM", Warning700)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(data.estimasiTiba ?: "12:15 - 12:45 WIB", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = black)

                Spacer(modifier = Modifier.height(24.dp))
                Text("Status Pengiriman", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
                Spacer(modifier = Modifier.height(16.dp))

                TimelineItem("Pesanan Diproses", data.waktuDiproses ?: "08.30 WIB", state = "DONE", isLast = false)
                TimelineItem("Kurir Berangkat", data.waktuBerangkat ?: "11.10 WIB", state = "DONE", isLast = false)
                TimelineItem("Dalam Perjalanan", "Sedang menuju sekolah Anda", state = "CURRENT", isLast = true, isTitleGreen = true)

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = BorderGray)
                Spacer(modifier = Modifier.height(16.dp))

                Text("Detail Pengiriman", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size(40.dp).background(GreenLight, CircleShape), contentAlignment = Alignment.Center) {
                        Image(painter = painterResource(id = R.drawable.emoji_chef), contentDescription = "Chef", modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Asal Dapur", fontSize = 12.sp, color = TextGray)
                        Text("Dapur MBG Klojen", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = black)
                    }
                    HubungiButton {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/+6281234567890"))
                        context.startActivity(intent)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.size(40.dp).background(GreenLight, CircleShape), contentAlignment = Alignment.Center) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Kurir", tint = GreenPrimary, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Kurir Pengantar", fontSize = 12.sp, color = TextGray)
                        Text(data.namaKurir ?: "Ahmad Kurniawan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = black)
                    }
                    HubungiButton {
                        val noTelp = data.telpKurir ?: "+6281234567890"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$noTelp"))
                        context.startActivity(intent)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = BorderGray)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.porsi_icon_ijo), contentDescription = "Porsi", tint = Color.Unspecified, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Jumlah Porsi", color = TextGray, fontSize = 14.sp)
                    }
                    Text("${data.porsi} Porsi", fontWeight = FontWeight.Bold, color = black, fontSize = 14.sp)
                }
            }
        }
    }
}

// ==========================================
// 3. UI KETIKA STATUS: DITERIMA
// ==========================================
@Composable
fun UIStatusDiterima(data: com.example.mbg.feature.distribusi.data.model.DistribusiModel, mapUrl: String) {

    // BANNER SELAMAT
    Card(
        colors = CardDefaults.cardColors(containerColor = GreenLight),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.confetti_icon),
                contentDescription = "Selamat",
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Selamat!", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = black)
            Spacer(modifier = Modifier.height(4.dp))
            Text("MBG telah sampai di sekolah", color = TextGray, fontSize = 14.sp)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // KARTU BUKTI PENGIRIMAN
    Card(colors = CardDefaults.cardColors(containerColor = White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Bukti Pengiriman", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
                StatusBadge(text = "TERKIRIM", color = GreenPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Diterima oleh ${data.namaPenerima}", fontWeight = FontWeight.Bold, color = black, fontSize = 14.sp)
                    Text("Satpam MAN 2 Kota Malang", color = TextGray, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.process_icon_ijo), contentDescription = "Time", tint = GreenPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(data.waktuDiterima ?: "12.20 WIB", color = TextGray, fontSize = 12.sp)
                    }
                }

                Box(modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)).background(BorderGray)) {
                    // Isi dengan AsyncImage kalau data foto tersedia
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Buka Foto */ },
                colors = ButtonDefaults.buttonColors(containerColor = Graybackground),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Lihat Foto", tint = black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Lihat Foto Lengkap", color = black, fontWeight = FontWeight.SemiBold)
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // KARTU MENU HARI INI
    Card(colors = CardDefaults.cardColors(containerColor = White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
        Column {
            Image(painter = painterResource(id = R.drawable.ayammenuutuh), contentDescription = "Menu", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth().height(150.dp))
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("Menu Hari Ini", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = black)
                        Text("Rencana Gizi Seimbang A", color = TextGray, fontSize = 14.sp)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("650", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = GreenPrimary)
                        Text("kkal / meal", color = TextGray, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    MenuBadge("Nasi")
                    Spacer(modifier = Modifier.width(8.dp))
                    MenuBadge("Ayam Bakar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                MenuBadge("Sayuran Kukus")
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // TIMELINE STATUS PENGIRIMAN DI PALING BAWAH
    Text("Status Pengiriman", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = black)
    Spacer(modifier = Modifier.height(16.dp))

    TimelineItem("Pesanan Diproses", data.waktuDiproses ?: "08.30 WIB", state = "DONE", isLast = false)
    TimelineItem("Kurir Berangkat", data.waktuBerangkat ?: "11.10 WIB", state = "DONE", isLast = false)
    TimelineItem("Tiba di Sekolah", "${data.waktuDiterima ?: "12.20 WIB"} - Diterima oleh ${data.namaPenerima}", state = "DONE", isLast = true)
}

// ==========================================
// KOMPONEN PENDUKUNG
// ==========================================

@Composable
fun HubungiButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
        modifier = Modifier.defaultMinSize(minWidth = 1.dp, minHeight = 32.dp)
    ) {
        Text("Hubungi", color = White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusBadge(text: String, color: Color) {
    Surface(shape = RoundedCornerShape(16.dp), border = BorderStroke(1.dp, color), color = White) {
        Text(text = text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

@Composable
fun MenuBadge(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = GreenLight
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
            Box(modifier = Modifier.size(8.dp).background(GreenPrimary, CircleShape))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, fontSize = 12.sp, color = Gray700)
        }
    }
}

@Composable
fun TimelineItem(title: String, time: String, state: String, isLast: Boolean, isTitleGreen: Boolean = false) {
    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                when (state) {
                    "DONE" -> {
                        Box(modifier = Modifier.size(24.dp).background(GreenPrimary, CircleShape), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Done", tint = White, modifier = Modifier.size(16.dp))
                        }
                    }
                    "CURRENT" -> {
                        Box(
                            modifier = Modifier.size(24.dp).background(GreenLight, CircleShape).border(2.dp, GreenPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.size(10.dp).background(GreenPrimary, CircleShape))
                        }
                    }
                }
            }
            if (!isLast) {
                Box(modifier = Modifier.width(2.dp).weight(1f).background(if (state == "DONE") GreenPrimary else BorderGray))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            val titleColor = if (isTitleGreen) GreenPrimary else if (state == "DONE" || state == "CURRENT") black else TextGray
            Text(text = title, fontWeight = if (state == "DONE" || state == "CURRENT") FontWeight.Bold else FontWeight.Normal, color = titleColor, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = time, fontSize = 14.sp, color = TextGray)
        }
    }
}