package com.example.mbg.feature.profile.presentation

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*

enum class UserRole {
    SEKOLAH, DAPUR, ORANGTUA
}

data class UserProfile(
    val role: UserRole,
    val name: String,
    val subtitle: String,
    val email: String,
    val phone: String
)

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onEditClick: () -> Unit = {},
    onFaqClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    // Udah disesuaikan sama nama asset lu
    val avatarRes = when (userProfile.role) {
        UserRole.SEKOLAH -> R.drawable.school_emoji
        UserRole.DAPUR -> R.drawable.chef_emoji
        UserRole.ORANGTUA -> R.drawable.oeangtua_emoji // (Kalau ini blm ada di folder, pake yg lama)
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Gray50)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(GreenPrimary)
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp).verticalScroll(scrollState)
        ) {
            Text(
                text = "Profil",
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(64.dp).clip(CircleShape).background(GreenLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(id = avatarRes), contentDescription = "Avatar", modifier = Modifier.size(40.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = userProfile.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = black)
                            Text(text = userProfile.subtitle, fontSize = 12.sp, color = TextGray)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    ContactRow(iconRes = R.drawable.email_icon, text = userProfile.email)
                    Spacer(modifier = Modifier.height(8.dp))
                    ContactRow(iconRes = R.drawable.phone_icon, text = userProfile.phone)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, BorderGray)
            ) {
                Column {
                    Text(
                        text = "Pengaturan", fontSize = 12.sp, fontWeight = FontWeight.Medium,
                        color = TextGray, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
                    )
                    HorizontalDivider(color = BorderGray)

                    SettingsItem(iconRes = R.drawable.profil_icon_blue, title = "Edit Profil", onClick = onEditClick)
                    HorizontalDivider(color = BorderGray, modifier = Modifier.padding(start = 52.dp))
                    SettingsItem(iconRes = R.drawable.notification_icon, title = "Notifikasi")
                    HorizontalDivider(color = BorderGray, modifier = Modifier.padding(start = 52.dp))
                    SettingsItem(iconRes = R.drawable.shield_icon, title = "Privasi & Keamanan")
                    HorizontalDivider(color = BorderGray, modifier = Modifier.padding(start = 52.dp))
                    SettingsItem(iconRes = R.drawable.faq_icon, title = "FAQ", onClick = onFaqClick)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            StatsCardBasedOnRole(role = userProfile.role)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Error300),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = White, contentColor = Error700)
            ) {
                Icon(painter = painterResource(id = R.drawable.keluar_merah), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Unspecified)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keluar", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun ContactRow(iconRes: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 12.sp, color = TextGray)
    }
}

@Composable
fun SettingsItem(iconRes: Int, title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = black, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextGray)
    }
}

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
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimary)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = title, color = White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = label1, color = White.copy(alpha = 0.8f), fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value1, color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = label2, color = White.copy(alpha = 0.8f), fontSize = 10.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = value2, color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }
    }
}