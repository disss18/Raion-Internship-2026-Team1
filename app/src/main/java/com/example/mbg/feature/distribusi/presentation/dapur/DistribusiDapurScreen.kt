package com.example.mbg.feature.distribusi.presentation.dapur

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mbg.R
import com.example.mbg.core.navigation.BottomNavConfig
import com.example.mbg.core.ui.component.DashboardBottomBar
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DistribusiDapurScreen(
    viewModel: DistribusiDapurViewModel,
    navController: NavController, // 🔥 Tambahan buat Navbar
    roleString: String?,          // 🔥 Tambahan buat Navbar
    distribusiId: String = "dummy-id-123"
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()
            viewModel.onFotoBuktiChange(bytes)
            inputStream?.close()
        }
    }

    val bottomNavItems = when (roleString) {
        "DAPUR_MBG" -> BottomNavConfig.mbg
        "SEKOLAH" -> BottomNavConfig.school
        else -> BottomNavConfig.parent
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Distribusi Hari Ini", color = White, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreenPrimary)
            )
        },
        // 🔥 BUNGKUS BOTTOM BAR DI SINI
        bottomBar = {
            DashboardBottomBar(navController = navController, items = bottomNavItems)
        },
        containerColor = BackgroundGray
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("MAN 2 Kota Malang", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("500 Paket Makanan - Jl. Bandung No.7", fontSize = 14.sp, color = TextGray)

                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = "https://maps.googleapis.com/maps/api/staticmap?center=-7.9604,112.6186&zoom=15&size=600x300&maptype=roadmap&markers=color:green%7C-7.9604,112.6186&key=YOUR_API_KEY",
                        contentDescription = "Peta Lokasi", contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(8.dp)).background(BorderGray)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        StepperIndicator("Diproses", R.drawable.process_icon_ijo, state.currentStatus == "diproses")
                        StepperIndicator("Dikirim", R.drawable.dikirim_icon_ijo, state.currentStatus == "dikirim")
                        StepperIndicator("Diterima", R.drawable.diterima_icon, state.currentStatus == "diterima")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    when (state.currentStatus) {
                        "dikirim" -> {
                            CustomInputField(
                                label = "Nama Kurir",
                                placeholder = "Masukkan nama kurir",
                                value = state.namaKurir,
                                onValueChange = viewModel::onNamaKurirChange
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            // 🔥 KEYBOARD TELEPON KHUSUS BUAT NOMOR KURIR
                            CustomInputField(
                                label = "Nomor Telpon Kurir",
                                placeholder = "Masukkan nomor telpon kurir",
                                value = state.telpKurir,
                                onValueChange = viewModel::onTelpKurirChange,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )
                        }
                        "diterima" -> {
                            CustomInputField("Nama Penerima", "Masukkan nama perwakilan sekolah", state.namaPenerima, viewModel::onNamaPenerimaChange)
                            Spacer(modifier = Modifier.height(16.dp))
                            CustomInputField("Deskripsi Penerima", "Masukkan Deskripsi Penerima", state.deskripsiPenerima, viewModel::onDeskripsiPenerimaChange)
                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Upload Bukti Foto", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = black)
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp)).background(Graybackground).clickable { imagePickerLauncher.launch("image/*") },
                                contentAlignment = Alignment.Center
                            ) {
                                if (state.fotoBukti != null) {
                                    Text("Foto berhasil dipilih!", color = GreenPrimary, fontWeight = FontWeight.Bold)
                                } else {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(painter = painterResource(id = R.drawable.ambilfotomenu_icon), contentDescription = "Tambah", tint = GreenPrimary, modifier = Modifier.size(32.dp))
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Ambil Foto Menu", color = TextGray, fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    PrimaryButton(
                        text = if (state.isLoading) "Memproses..." else "Konfirmasi Status",
                        containerColor = GreenPrimary, enabled = !state.isLoading,
                        onClick = { viewModel.konfirmasiStatus(distribusiId) }
                    )

                    if (state.errorMessage != null) {
                        Text(text = state.errorMessage!!, color = Error700, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }

    if (state.isSuccess) {
        Dialog(onDismissRequest = { viewModel.dismissSuccessDialog() }) {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = White), modifier = Modifier.padding(16.dp)) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.centang_succes),
                        contentDescription = "Success",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Status Berhasil Diperbarui", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, color = black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Status distribusi makanan MBG telah berhasil diperbarui.", color = TextGray, textAlign = TextAlign.Center, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(24.dp))
                    PrimaryButton("Ok", onClick = { viewModel.dismissSuccessDialog() }, containerColor = GreenPrimary)
                }
            }
        }
    }
}

// 🔥 DITAMBAHIN KEYBOARD OPTIONS
@Composable
fun CustomInputField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = black)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = value, onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = TextGray, fontSize = 14.sp) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Graybackground, unfocusedContainerColor = Graybackground,
                focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp), singleLine = true
        )
    }
}

@Composable
fun StepperIndicator(text: String, iconRes: Int, isActive: Boolean) {
    val color = if (isActive) GreenPrimary else TextGray
    val bgColor = if (isActive) GreenLight else White
    val borderColor = if (isActive) GreenPrimary else BorderGray

    Column(
        modifier = Modifier.width(95.dp).height(95.dp).border(BorderStroke(1.dp, borderColor), RoundedCornerShape(12.dp)).background(bgColor, RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = text, tint = color, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, color = color, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}