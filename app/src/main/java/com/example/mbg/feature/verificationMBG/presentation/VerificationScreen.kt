package com.example.mbg.feature.verificationMBG.presentation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*
import com.example.mbg.core.ui.component.button.PrimaryButton
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.verificationMBG.VerificationViewModel

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
    val viewModel: VerificationViewModel = viewModel()

    var currentStep by remember { mutableIntStateOf(initialStep) }
    val context = LocalContext.current

    var namaUmkm by remember { mutableStateOf(initNamaUmkm) }
    var alamat by remember { mutableStateOf(initAlamat) }
    var namaPemilik by remember { mutableStateOf(initPemilik) }
    var isAgreed by remember { mutableStateOf(initAgreed) }

    var fotoUsahaUri by remember { mutableStateOf<Uri?>(null) }
    var fotoKtpUri by remember { mutableStateOf<Uri?>(null) }
    var dokumenUri by remember { mutableStateOf<Uri?>(null) }
    var buktiTransferUri by remember { mutableStateOf<Uri?>(null) }

    val usahaLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> fotoUsahaUri = uri }
    val ktpLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> fotoKtpUri = uri }
    val dokumenLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> dokumenUri = uri }
    val buktiLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri -> buktiTransferUri = uri }

    val isStep1Valid = namaUmkm.isNotBlank() && alamat.isNotBlank() && (fotoUsahaUri != null) && namaPemilik.isNotBlank()
    val isStep2Valid = (fotoKtpUri != null) && (dokumenUri != null) && (buktiTransferUri != null)
    val isStep3Valid = isAgreed

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = Gray900)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFAFAFA))
            )
        },
        containerColor = Color(0xFFFAFAFA)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Bagian Header (dikasih padding horizontal)
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Verifikasi Dapur MBG", fontFamily = poppins, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Gray900)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Verifikasi UMKM untuk menjadi dapur penyedia\nMBG", fontFamily = poppins, fontSize = 12.sp, color = Gray500, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(32.dp))

                StepperIndicator(currentStep = currentStep)
                Spacer(modifier = Modifier.height(24.dp))
            }

            HorizontalDivider(thickness = 1.dp, color = Color(0xFFE5E7EB))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(24.dp))

                when (currentStep) {
                    1 -> {
                        CustomTextFieldCard("Nama UMKM", "Masukkan nama usaha", namaUmkm, R.drawable.usaha_icon) { namaUmkm = it }
                        CustomTextFieldCard("Alamat Lengkap", "Masukkan alamat lengkap termasuk kota dan kode pos", alamat, R.drawable.pinpoint_icon, true) { alamat = it }

                        ReusableUploadCard(
                            label = "Upload Foto Tempat Usaha",
                            subLabel = null,
                            isUploaded = fotoUsahaUri != null,
                            headerIconRes = R.drawable.pinpoint_icon,
                            centerIconRes = R.drawable.upload_icon,
                            centerText = "Klik untuk upload atau drag & drop",
                            isCompact = true
                        ) { usahaLauncher.launch("image/*") }

                        CustomTextFieldCard("Nama Pemilik", "Masukkan nama pemilik sesuai KTP", namaPemilik, R.drawable.pemilik_icon_biru) { namaPemilik = it }
                    }
                    2 -> {
                        ReusableUploadCard(
                            label = "Upload Foto KTP Pemilik",
                            subLabel = null,
                            isUploaded = fotoKtpUri != null,
                            headerIconRes = R.drawable.ktp_icon_biru,
                            centerIconRes = R.drawable.upload_icon,
                            centerText = "Upload foto KTP Pemilik",
                            isCompact = true
                        ) { ktpLauncher.launch("image/*") }

                        Spacer(modifier = Modifier.height(16.dp))

                        ReusableUploadCard(
                            label = "Dokumen Keuangan",
                            subLabel = "Upload mutasi rekening atau laporan keuangan",
                            isUploaded = dokumenUri != null,
                            headerIconRes = R.drawable.document_icon_biru,
                            centerIconRes = R.drawable.upload_icon,
                            centerText = "Upload dokumen",
                            isCompact = true
                        ) { dokumenLauncher.launch("application/pdf,image/*") }

                        Spacer(modifier = Modifier.height(16.dp))

                        ReusableUploadCard(
                            label = "Bukti Transfer",
                            subLabel = "Untuk melanjutkan pendaftaran sebagai Dapur MBG, UMKM dikenakan biaya verifikasi sebesar Rp500.000.\n\nBank BCA\nNo. Rekening: 1234567890\nAtas Nama: PT Program Gizi Nusantara",
                            isUploaded = buktiTransferUri != null,
                            headerIconRes = R.drawable.document_icon_biru,
                            centerIconRes = R.drawable.upload_icon,
                            centerText = "Upload dokumen",
                            isCompact = true
                        ) { buktiLauncher.launch("image/*") }
                    }
                    3 -> {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.Top) {
                                Checkbox(checked = isAgreed, onCheckedChange = { isAgreed = it }, colors = CheckboxDefaults.colors(checkedColor = FoundationGreen, uncheckedColor = Gray500), modifier = Modifier.size(24.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Saya menyatakan bahwa informasi yang disampaikan adalah benar dan saya berkomitmen untuk menjaga standar gizi makanan jika diterima sebagai mitra dapur MBG.", fontFamily = poppins, fontSize = 12.sp, color = Gray700, lineHeight = 18.sp)
                            }
                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = White),
                            border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text("Ringkasan Verifikasi", fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.padding(bottom = 16.dp))
                                SummaryRow("Nama UMKM", if(namaUmkm.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Alamat", if(alamat.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Nama Pemilik", if(namaPemilik.isNotBlank()) "Sudah Upload" else "Belum")
                                SummaryRow("Foto Usaha", if(fotoUsahaUri != null) "Sudah Upload" else "Belum")
                                SummaryRow("KTP", if(fotoKtpUri != null) "Sudah Upload" else "Belum")
                                SummaryRow("Dokumen Keuangan", if(dokumenUri != null) "Sudah Upload" else "Belum")
                                SummaryRow("Bukti Transfer", if(buktiTransferUri != null) "Sudah Upload" else "Belum")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStep > 1) {
                        OutlinedButton(
                            onClick = { currentStep-- },
                            modifier = Modifier.weight(1f).height(35.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, FoundationGreen),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Sebelum", fontFamily = poppins, fontWeight = FontWeight.Bold, color = FoundationGreen, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    val isBtnEnabled = when(currentStep) { 1 -> isStep1Valid; 2 -> isStep2Valid; 3 -> isStep3Valid; else -> false }

                    Box(modifier = Modifier.weight(1f)) {
                        PrimaryButton(
                            text = if (currentStep == 3) "Verifikasi" else "Selanjutnya",
                            enabled = isBtnEnabled,
                            containerColor = if (isBtnEnabled) FoundationGreen else Color(0xFFE5E7EB),
                            contentColor = if (isBtnEnabled) White else Gray500,
                            modifier = Modifier.height(35.dp),
                            onClick = {
                                if (currentStep < 3) {
                                    currentStep++
                                } else {
                                    viewModel.submitVerifikasi(
                                        context = context,
                                        namaUmkm = namaUmkm,
                                        alamat = alamat,
                                        namaPemilik = namaPemilik,
                                        fotoUsahaUri = fotoUsahaUri,
                                        fotoKtpUri = fotoKtpUri,
                                        dokumenUri = dokumenUri,
                                        buktiTransferUri = buktiTransferUri,
                                        onSuccess = {
                                            onSubmitSuccess()
                                        }
                                    )
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}


@Composable
fun StepperIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        StepItem(1, "Data Usaha", currentStep, Modifier.width(80.dp))
        StepLine(isActive = currentStep >= 2, modifier = Modifier.weight(1f).padding(top = 16.dp, start = 4.dp, end = 4.dp))
        StepItem(2, "Dokumen", currentStep, Modifier.width(80.dp))
        StepLine(isActive = currentStep >= 3, modifier = Modifier.weight(1f).padding(top = 16.dp, start = 4.dp, end = 4.dp))
        StepItem(3, "Verifikasi", currentStep, Modifier.width(80.dp))
    }
}

@Composable
fun StepItem(step: Int, label: String, currentStep: Int, modifier: Modifier = Modifier) {
    val isCompleted = step < currentStep
    val isActive = step == currentStep
    val bgColor = if (isCompleted || isActive) FoundationGreen else Color(0xFFD1D5DB)
    val contentColor = Gray500

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(bgColor), contentAlignment = Alignment.Center) {
            if (isCompleted) {
                Icon(painter = painterResource(id = R.drawable.centang_hitam_icon), contentDescription = null, modifier = Modifier.size(18.dp), tint = White)
            } else {
                Text(step.toString(), color = if (isActive || isCompleted) White else contentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontFamily = poppins, fontSize = 10.sp, color = if (isActive || isCompleted) Gray900 else Gray500, textAlign = TextAlign.Center)
    }
}

@Composable
fun StepLine(isActive: Boolean, modifier: Modifier) {
    Box(modifier = modifier.height(2.dp).background(if (isActive) FoundationGreen else Color(0xFFD1D5DB)))
}

@Composable
fun CustomTextFieldCard(label: String, placeholder: String, value: String, iconRes: Int, isMultiline: Boolean = false, onValueChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = iconRes), contentDescription = null, tint = FoundationGreen, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(label, fontFamily = poppins, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray900)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder, color = Gray500, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF3F4F6),
                    unfocusedContainerColor = Color(0xFFF3F4F6),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                singleLine = !isMultiline,
                maxLines = if (isMultiline) 4 else 1
            )
        }
    }
}

@Composable
fun ReusableUploadCard(
    label: String,
    subLabel: String? = null,
    isUploaded: Boolean,
    headerIconRes: Int,
    centerIconRes: Int,
    centerText: String,
    isCompact: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = headerIconRes), contentDescription = null, tint = FoundationGreen, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(label, fontFamily = poppins, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Gray900)
            }
            if (subLabel != null) {
                Text(text = subLabel, fontFamily = poppins, fontSize = 11.sp, color = Gray500, modifier = Modifier.padding(top = 4.dp, start = 28.dp, bottom = 12.dp))
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF3F4F6))
                    .border(1.dp, if (isUploaded) FoundationGreen else Color.Transparent, RoundedCornerShape(8.dp))
                    .clickable { onClick() }
                    .padding(vertical = if (isCompact) 16.dp else 40.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isCompact) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        if (isUploaded) {
                            Icon(painter = painterResource(id = R.drawable.centang_hitam_icon), contentDescription = null, tint = FoundationGreen, modifier = Modifier.size(20.dp))
                        } else {
                            Icon(painter = painterResource(id = centerIconRes), contentDescription = null, tint = Gray500, modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isUploaded) "File Berhasil Diupload" else centerText,
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isUploaded) FoundationGreen else Gray700
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (isUploaded) {
                            Icon(painter = painterResource(id = R.drawable.centang_hitam_icon), contentDescription = null, tint = FoundationGreen, modifier = Modifier.size(28.dp))
                        } else {
                            Icon(painter = painterResource(id = centerIconRes), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(28.dp))
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = if (isUploaded) "File Berhasil Diupload" else centerText,
                            fontFamily = poppins,
                            fontSize = 11.sp,
                            color = if (isUploaded) FoundationGreen else Gray500
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, status: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontFamily = poppins, fontSize = 12.sp, color = Gray700)
        Text(text = status, fontFamily = poppins, fontSize = 12.sp, color = if(status == "Belum") Error700 else Gray700, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true, name = "1. Step Data Usaha")
@Composable
fun PreviewStep1() {
    MBGTheme {
        VerificationScreen(initialStep = 1)
    }
}

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
            initPemilik = "Abang Jago",
            initAgreed = true
        )
    }
}