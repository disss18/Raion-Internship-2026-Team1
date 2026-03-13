package com.example.mbg.feature.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    role: UserRole,
    onBackClick: () -> Unit
) {
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profil", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White) }
                },
                actions = {
                    IconButton(onClick = { showSuccessDialog = true }) { Icon(Icons.Default.Check, contentDescription = "Save", tint = White) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        containerColor = BackgroundGray
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔥 STRUKTUR BARU BIAR GAK NUMPUK
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Background hijau
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(GreenPrimary)
                )

                // Avatar (posisinya di tengah dan nonjol ke bawah)
                Box(
                    modifier = Modifier
                        .padding(top = 60.dp) // Dorong ke bawah biar nonjol
                        .align(Alignment.TopCenter)
                ) {
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape).background(White),
                        contentAlignment = Alignment.Center
                    ) {
                        val avatarRes = when(role) {
                            UserRole.SEKOLAH -> R.drawable.school_emoji
                            UserRole.DAPUR -> R.drawable.chef_emoji
                            UserRole.ORANGTUA -> R.drawable.oeangtua_emoji
                        }
                        Icon(painter = painterResource(id = avatarRes), contentDescription = null, modifier = Modifier.size(50.dp), tint = Color.Unspecified)
                    }
                    Box(
                        modifier = Modifier.align(Alignment.BottomEnd).size(28.dp).clip(CircleShape).background(GreenPrimary).padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ambilfotomenu_icon), contentDescription = null, tint = White, modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Jarak aman foto sama card

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    when (role) {
                        UserRole.SEKOLAH -> {
                            EditProfileItemField("Nama Sekolah", "MAN 2 Malang")
                            EditProfileItemField("Email", "man2malang@gmail.com")
                            EditProfileItemField("No. Telp", "+62 8123 4567 8910")
                            EditProfileItemField("NPSN", "69955823")
                            EditProfileItemField("Alamat", "Jl. Bandung No.7")
                        }
                        UserRole.DAPUR -> {
                            EditProfileItemField("Nama Pemilik", "Marhaban")
                            EditProfileItemField("Nama UMKM", "Dapur Klojen")
                            EditProfileItemField("Email", "kitchen1@mbg.gov")
                            EditProfileItemField("No. Telp", "+1 (555) 987-6543")
                            EditProfileItemField("Alamat", "Jl. Soekarno Hatta")
                        }
                        UserRole.ORANGTUA -> {
                            EditProfileItemField("Nama Orangtua", "Masyithah")
                            EditProfileItemField("Nama Anak", "Rameyza")
                            EditProfileItemField("NISN", "00919137113034743508")
                            EditProfileItemField("Email", "masyithah@gmail.com")
                            EditProfileItemField("No. Telp", "+62 8888 1111 9999")
                            EditProfileItemField("Nama Sekolah Anak", "MAN 2 Malang")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showSuccessDialog) {
        Dialog(onDismissRequest = { showSuccessDialog = false }) {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = White)) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(id = R.drawable.centang_success), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Profil Berhasil Diperbarui", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Perubahan pada profil kamu telah berhasil disimpan.", color = TextGray, fontSize = 14.sp, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton("Ok", onClick = { showSuccessDialog = false; onBackClick() }, containerColor = GreenPrimary)
                }
            }
        }
    }
}

@Composable
fun EditProfileItemField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value, onValueChange = {}, readOnly = false,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Graybackground, unfocusedContainerColor = Graybackground,
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp), singleLine = true
        )
    }
}