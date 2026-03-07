package com.example.mbg.verificationMBG

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
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.mbg.R
    import com.example.mbg.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    initialStep: Int = 1,
    initNamaUmkm: String = "",
    initAlamat: String = "",
    initPemilik: String = "",
    initAgreed: Boolean = false,
    onBackClick: () -> Unit = {},
    onSubmitSuccess: () -> Unit = {}
) {
    var currentStep by remember { mutableIntStateOf(initialStep) }
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel: VerificationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // 1. STATE INPUTAN (Taruh semua variabel teks di sini paling atas)
    var namaUmkm by remember { mutableStateOf(initNamaUmkm) }
    var alamat by remember { mutableStateOf(initAlamat) }
    var namaPemilik by remember { mutableStateOf(initPemilik) } // ✅ Sekarang sudah di atas
    var isAgreed by remember { mutableStateOf(initAgreed) }

    // 2. STATE FOTO/FILE (URI)
    var fotoUsahaUri by remember { mutableStateOf<Uri?>(null) }
    var fotoKtpUri by remember { mutableStateOf<Uri?>(null) }
    var dokumenUri by remember { mutableStateOf<Uri?>(null) }

    // 3. MESIN PEMBUKA GALERI (Launchers)
    val usahaLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> fotoUsahaUri = uri }

    val ktpLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> fotoKtpUri = uri }

    val dokumenLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> dokumenUri = uri }

    // 4. VALIDASI (Sekarang aman karena semua variabel di atas sudah "dikenal")
// 4. VALIDASI
    val isStep1Valid = namaUmkm.isNotBlank() && alamat.isNotBlank() && (fotoUsahaUri != null) && namaPemilik.isNotBlank()
    val isStep2Valid = (fotoKtpUri != null) && (dokumenUri != null)
    val isStep3Valid = isAgreed

    // --- SISA KODINGAN SCAFFOLD KE BAWAH TETAP SAMA ---
    
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Gray900)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
                )
            },
            // 🔥 TOMBOL SEKARANG DI-PIN DI PALING BAWAH LAYAR
            bottomBar = {
                Surface(
                    color = White,
                    shadowElevation = 8.dp // Efek bayangan di atas tombol
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentStep > 1) {
                            OutlinedButton(
                                onClick = { currentStep-- },
                                modifier = Modifier.weight(1f).height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFFD1D5DB))
                            ) {
                                Text("Sebelumnya", fontFamily = poppins, fontWeight = FontWeight.Bold, color = Gray700)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                        }

                        Button(
                            onClick = {
                                if (currentStep < 3) {
                                    currentStep++
                                } else {
                                    // 🔥 DATA TERBANG KE SUPABASE DISINI 🔥
                                    viewModel.submitVerifikasi(
                                        context = context,
                                        namaUmkm = namaUmkm,
                                        alamat = alamat,
                                        namaPemilik = namaPemilik,
                                        fotoUsahaUri = fotoUsahaUri,
                                        fotoKtpUri = fotoKtpUri,
                                        dokumenUri = dokumenUri,
                                        onSuccess = {
                                            onSubmitSuccess() // Pindah layar kalau sukses
                                        }
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BlueNormal, disabledContainerColor = Color(0xFFE5E7EB)),
                            enabled = when(currentStep) { 1 -> isStep1Valid; 2 -> isStep2Valid; 3 -> isStep3Valid; else -> false }
                        ) {
                            Text(
                                text = if (currentStep == 3) "Submit Verifikasi" else "Selanjutnya",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Bold, // Font lebih tebal
                                color = if (when(currentStep){ 1->isStep1Valid; 2->isStep2Valid; 3->isStep3Valid; else->false }) White else Gray500,
                                maxLines = 1,
                                softWrap = false
                            )
                        }
                    }
                }
            },
            containerColor = Color(0xFFFAFAFA) // Latar agak abu biar shadow kelihatan
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Text("Verifikasi Dapur MBG", fontFamily = poppins, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gray900)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Verifikasi UMKM untuk menjadi dapur penyedia\nMBG",
                    fontFamily = poppins,
                    fontSize = 12.sp,
                    color = Gray500,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
    
                // Stepper
                StepperIndicator(currentStep = currentStep)
                Spacer(modifier = Modifier.height(32.dp))
    
                // Konten Step
                when (currentStep) {
                    1 -> {
                        CustomTextFieldCard("Nama UMKM", "Masukkan nama usaha", namaUmkm, R.drawable.usaha_icon) { namaUmkm = it }
                        CustomTextFieldCard("Alamat Lengkap", "Masukkan alamat lengkap termasuk kota dan kode pos", alamat, R.drawable.pinpoint_icon, true) { alamat = it }
                        UploadBoxCard("Upload Foto Tempat Usaha", null, fotoUsahaUri != null, R.drawable.usaha_icon, centerIconRes = R.drawable.upload_icon, centerText = "Klik untuk upload atau drag & drop") { usahaLauncher.launch("image/*") }
                        CustomTextFieldCard("Nama Pemilik", "Masukkan nama pemilik sesuai KTP", namaPemilik, R.drawable.pemilik_icon_biru) { namaPemilik = it }
                    }
                    2 -> {
                        // Pakai ktp_icon_abu di tengah
                        UploadBoxCard("Upload Foto KTP Pemilik",
                                        null,
                                        fotoKtpUri != null,
                                        R.drawable.ktp_icon_biru,
                                        centerIconRes = R.drawable.ktp_icon_abu,
                                        centerText = "Upload foto KTP Pemilik")
                                        { ktpLauncher.launch("image/*") }
                        // Pakai upload dokumen 1 baris
                        CompactUploadBoxCard(
                            label = "Dokumen Keuangan",
                            subLabel = "Upload mutasi rekening atau laporan keuangan",
                            dokumenUri != null,
                            headerIconRes = R.drawable.document_icon_biru,
                            buttonText = "Upload dokumen" )
                            { dokumenLauncher.launch("application/pdf,image/*") }
                    }
                    3 -> {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.Top) {
                                Checkbox(
                                    checked = isAgreed,
                                    onCheckedChange = { isAgreed = it },
                                    colors = CheckboxDefaults.colors(checkedColor = BlueNormal, uncheckedColor = Gray500),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Saya menyatakan bahwa informasi yang disampaikan adalah benar dan saya berkomitmen untuk menjaga standar gizi makanan jika diterima sebagai mitra dapur MBG.",
                                    fontFamily = poppins,
                                    fontSize = 12.sp,
                                    color = Gray700,
                                    lineHeight = 18.sp
                                )
                            }
                        }
    
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Ringkasan Verifikasi", fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))
                                SummaryRow("Nama UMKM", if(namaUmkm.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Alamat", if(alamat.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Nama Pemilik", if(namaPemilik.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Foto Usaha", if(fotoUsahaUri != null) "Sudah Upload" else "Belum")
                                SummaryRow("KTP", if(fotoKtpUri != null) "Sudah Upload" else "Belum")
                                SummaryRow("Dokumen Keuangan", if(dokumenUri != null) "Sudah Upload" else "Belum")
                            }
                        }
                    }
                }
    
                // Sedikit jarak tambahan di bawah biar lega pas di-scroll
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
    
    // ==========================================
    // KOMPONEN BANTUAN UI
    // ==========================================
    
    @Composable
    fun StepperIndicator(currentStep: Int) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top) {
            StepItem(1, "Data Usaha", currentStep, Modifier.width(80.dp))
            // Garis lebih pendek (30.dp) dan warna lebih gelap
            StepLine(currentStep >= 2, Modifier.width(30.dp).padding(top = 16.dp))
            StepItem(2, "Dokumen", currentStep, Modifier.width(80.dp))
            StepLine(currentStep >= 3, Modifier.width(30.dp).padding(top = 16.dp))
            StepItem(3, "Verifikasi", currentStep, Modifier.width(80.dp))
        }
    }
    
    @Composable
    fun StepItem(step: Int, label: String, currentStep: Int, modifier: Modifier = Modifier) {
        val isCompleted = step < currentStep
        val isActive = step == currentStep
        val bgColor = if (isCompleted || isActive) BlueNormal else Color(0xFFD1D5DB) // Warna abu lingkaran lebih gelap
        val contentColor = Gray500
    
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(bgColor), contentAlignment = Alignment.Center) {
                if (isCompleted) {
                    // PAKAI IKON CENTANG HITAM ABANG (TANPA TINT PUTIH)
                    Icon(painter = painterResource(id = R.drawable.centang_hitam_icon), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Unspecified)
                } else {
                    Text(step.toString(), color = contentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(label, fontFamily = poppins, fontSize = 10.sp, color = if (isActive || isCompleted) Gray900 else Gray500, textAlign = TextAlign.Center)
        }
    }
    
    @Composable
    fun StepLine(isActive: Boolean, modifier: Modifier) {
        Box(modifier = modifier.height(2.dp).background(if (isActive) BlueNormal else Color(0xFFD1D5DB)))
    }
    
    @Composable
    fun CustomTextFieldCard(label: String, placeholder: String, value: String, iconRes: Int, isMultiline: Boolean = false, onValueChange: (String) -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
            elevation = CardDefaults.cardElevation(2.dp), // Shadow dihidupkan
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = BlueNormal, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(label, fontFamily = poppins, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray900)
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(placeholder, color = Gray500, fontSize = 12.sp) },
                    // Modifier tinggi dibuat fleksibel (tidak dipatok mati), otomatis membesar sesuai karakter!
                    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F4F6), // Abu-abu lebih gelap
                        unfocusedContainerColor = Color(0xFFF3F4F6),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    singleLine = !isMultiline,
                    maxLines = if (isMultiline) 4 else 1 // Bisa membesar otomatis sampai 4 baris
                )
            }
        }
    }
    
    @Composable
    fun UploadBoxCard(label: String, subLabel: String?, isUploaded: Boolean, iconRes: Int, centerIconRes: Int, centerText: String, onClick: () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
            elevation = CardDefaults.cardElevation(2.dp), // Shadow dihidupkan
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = BlueNormal, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(label, fontFamily = poppins, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray900)
                }
                if (subLabel != null) {
                    Text(
                        text = subLabel,
                        fontFamily = poppins,
                        fontSize = 11.sp,
                        color = Gray500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp, start = 28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
    
                // KOTAK UPLOAD COMPACT & SOLID BORDER
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF3F4F6)) // Abu-abu lebih gelap
                        .border(1.dp, if (isUploaded) BlueNormal else Color.Transparent, RoundedCornerShape(8.dp))
                        .clickable { onClick() }
                        .padding(vertical = 24.dp), // Compact size
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isUploaded) {
                            // Centang hitam dari abang pas udah keupload
                            Icon(painter = painterResource(id = R.drawable.centang_hitam_icon), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(28.dp))
                        } else {
                            // Icon dinamis (KTP abu / Upload biasa)
                            Icon(painter = painterResource(id = centerIconRes), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(28.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(if (isUploaded) "File Berhasil Diupload" else centerText, fontFamily = poppins, fontSize = 11.sp, color = if (isUploaded) BlueNormal else Gray500)
                    }
                }
            }
        }
    }
    
    @Composable
    fun SummaryRow(label: String, status: String) {
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, fontFamily = poppins, fontSize = 12.sp, color = Gray700)
            Text(
                text = status,
                fontFamily = poppins,
                fontSize = 12.sp,
                color = if(status == "Belum") Error700 else Gray700,
                fontWeight = FontWeight.Medium
            )
        }
    }
    
    // ==========================================
    // PREVIEW 3 STEP (FULL DATA UNTUK STEP 3)
    // ==========================================
    
    @Preview(showBackground = true, name = "1. Step Data Usaha")
    @Composable
    fun PreviewStep1() { MBGTheme { VerificationScreen(initialStep = 1) } }
    

    @Preview(showBackground = true, name = "2. Step Dokumen")
    @Composable
    fun PreviewStep2() {
        MBGTheme {
            VerificationScreen(
                initialStep = 2,
                initNamaUmkm = "Dapur Abang",
                initAlamat = "Jl. Malang",
                initPemilik = "Abang Jago"
            )
        }
    }

@Preview(showBackground = true, name = "3. Step Verifikasi (Full)")
@Composable
fun PreviewStep3() {
    MBGTheme {
        VerificationScreen(
            initialStep = 3,
            initNamaUmkm = "Dapur Abang",
            initAlamat = "Jl. Malang",
            initPemilik = "Abang Jago"
        )
    }
}
    
    @Composable
    fun CompactUploadBoxCard(label: String, subLabel: String?, isUploaded: Boolean, headerIconRes: Int, buttonText: String, onClick: () -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = headerIconRes),
                        contentDescription = null,
                        tint = BlueNormal,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        label,
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray900
                    )
                }
                if (subLabel != null) {
                    Text(
                        text = subLabel,
                        fontFamily = poppins,
                        fontSize = 11.sp,
                        color = Gray500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp, start = 28.dp, bottom = 12.dp)
                    )
                } else {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF3F4F6))
                        .border(
                            1.dp,
                            if (isUploaded) BlueNormal else Color.Transparent,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isUploaded) {
                            Icon(
                                painter = painterResource(id = R.drawable.centang_hitam_icon),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.upload_icon),
                                contentDescription = null,
                                tint = Gray500,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isUploaded) "File Berhasil Diupload" else buttonText,
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isUploaded) BlueNormal else Gray500
                        )
                    }
                }
            }
        }
    }