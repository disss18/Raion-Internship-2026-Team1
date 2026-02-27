package com.example.mbg.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*

// PENGATURAN DATA & LOGIKA ROLE
enum class UserRole {
    SEKOLAH, DAPUR, ORANGTUA
}

// berisi informasi profil yang akan ditampilkan di layar
data class UserProfile(
    val role: UserRole,
    val name: String,
    val subtitle: String,
    val email: String,
    val phone: String
)



// TAMPILAN UTAMA (SCREEN)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onLogoutClick: () -> Unit = {} // ketika tombol keluar ditekan nnti ke mana
) {
    val scrollState = rememberScrollState()

    // Logika Avatar: Otomatis ganti gambar berdasarkan Role yang login
    val avatarRes = when (userProfile.role) {
        UserRole.SEKOLAH -> R.drawable.sekolah_emoji
        UserRole.DAPUR -> R.drawable.dapur_emoji
        UserRole.ORANGTUA -> R.drawable.oeangtua_emoji
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray50)
    ) {
        // background lengkung ujungny
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(BlueNormal)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            // Judul Layar
            Text(
                text = "Profil",
                fontFamily = poppins,
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
            )

            // KARTU PROFIL UTAMA (Nama, Avatar, Kontak)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Lingkaran Avatar
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(BlueLightHover),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = avatarRes),
                                contentDescription = "Avatar Profil",
                                modifier = Modifier.size(40.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Nama dan Jabatan/Subtitle
                        Column {
                            Text(
                                text = userProfile.name,
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray900
                            )
                            Text(
                                text = userProfile.subtitle,
                                fontFamily = poppins,
                                fontSize = 12.sp,
                                color = Gray500
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Info Kontak
                    ContactRow(iconRes = R.drawable.email_icon, text = userProfile.email)
                    Spacer(modifier = Modifier.height(8.dp))
                    ContactRow(iconRes = R.drawable.telephone_icon, text = userProfile.phone)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MENU PENGATURAN

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, Color(0xFFF3F4F6)), // Border abu-abu tipis
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column {
                    // Judul di dalam Card
                    Text(
                        text = "Pengaturan",
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Gray500,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
                    )

                    // Garis panjang di bawah judul
                    HorizontalDivider(color = Color(0xFFF3F4F6))

                    // Menu 1: Edit Profil
                    SettingsItem(iconRes = R.drawable.profil_icon, title = "Edit Profil")

                    // Garis pemisah menjorok ke dalam sejajar dengan teks
                    HorizontalDivider(
                        color = Color(0xFFF3F4F6),
                        modifier = Modifier.padding(start = 52.dp)
                    )

                    // Menu 2: Notifikasi
                    SettingsItem(iconRes = R.drawable.notifikasi_icon, title = "Notifikasi")

                    // Garis pemisah menjorok ke dalam sejajar dengan teks
                    HorizontalDivider(
                        color = Color(0xFFF3F4F6),
                        modifier = Modifier.padding(start = 52.dp)
                    )

                    // Menu 3: Privasi & Keamanan
                    SettingsItem(iconRes = R.drawable.keamanan_icon, title = "Privasi & Keamanan")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))



            // KARTU STATISTIK (Berubah Otomatis Sesuai Role)
            StatsCardBasedOnRole(role = userProfile.role)

            Spacer(modifier = Modifier.height(32.dp))


            // TOMBOL KELUAR
            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Error300), // Border merah muda
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = White,
                    contentColor = Error700 // Teks merah terang
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.keluar_icon_merah),
                    contentDescription = "Ikon Keluar",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            // Jarak ekstra di bawah biar ga ketutup Bottom Navbar
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}


// Komponen untuk baris Info Kontak
@Composable
fun ContactRow(iconRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontFamily = poppins,
            fontSize = 12.sp,
            color = Gray500
        )
    }
}

// Komponen untuk satu baris menu Pengaturan
@Composable
fun SettingsItem(iconRes: Int, title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // efek ripple
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontFamily = poppins,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Gray900,
            modifier = Modifier.weight(1f) // Mendorong panah ke paling kanan
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Panah Kanan",
            tint = Gray500
        )
    }
}

// Komponen untuk Kartu Statistik (Teks & Angka dinamis)
@Composable
fun StatsCardBasedOnRole(role: UserRole) {
    val title = if (role == UserRole.ORANGTUA) "Aktivitas Pengguna" else "Dampak Program"
    val label1 = when (role) {
        UserRole.SEKOLAH -> "Siswa Dilayani"
        UserRole.DAPUR -> "Porsi disiapkan"
        UserRole.ORANGTUA -> "Pemantauan Anak"
    }
    val value1 = when (role) {
        UserRole.SEKOLAH -> "1,086"
        UserRole.DAPUR -> "12,786"
        UserRole.ORANGTUA -> "58"
    }
    val label2 = if (role == UserRole.ORANGTUA) "Artikel Edukasi Dibaca" else "Bulan Ini"
    val value2 = when (role) {
        UserRole.SEKOLAH -> "10,923"
        UserRole.DAPUR -> "48,998"
        UserRole.ORANGTUA -> "30"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BlueNormal)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontFamily = poppins,
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label1,
                        fontFamily = poppins,
                        color = White.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = value1,
                        fontFamily = poppins,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = label2,
                        fontFamily = poppins,
                        color = White.copy(alpha = 0.8f),
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = value2,
                        fontFamily = poppins,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}






@Preview(showBackground = true)
@Composable
fun PreviewProfileSekolah() {
    MBGTheme {
        ProfileScreen(
            userProfile = UserProfile(
                role = UserRole.SEKOLAH,
                name = "MAN 2 Malang",
                subtitle = "Admin MBG Sekolah",
                email = "man2malang@gmail.com",
                phone = "+62 8123 4567 8910"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileDapur() {
    MBGTheme {
        ProfileScreen(
            userProfile = UserProfile(
                role = UserRole.DAPUR,
                name = "Dapur Klojen",
                subtitle = "Staf Dapur MBG",
                email = "kitchen1@mbg.gov",
                phone = "+1 (555) 987-6543"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileOrangtua() {
    MBGTheme {
        ProfileScreen(
            userProfile = UserProfile(
                role = UserRole.ORANGTUA,
                name = "Masyithah",
                subtitle = "Orangtua",
                email = "masyithah@gmail.com",
                phone = "+62 8888 1111 9999"
            )
        )
    }
}