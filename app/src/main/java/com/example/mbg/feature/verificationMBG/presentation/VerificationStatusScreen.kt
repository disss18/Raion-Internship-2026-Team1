package com.example.mbg.feature.verificationMBG.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

enum class VerifStatus { PENDING, SUCCESS, FAILED }

data class StatusUI(val iconRes: Int, val title: String, val desc: String, val btnText: String)

@Composable
fun VerificationStatusScreen(
    status: VerifStatus,
    onNextClick: () -> Unit = {},
    onRefreshClick: () -> Unit = {} // 🔥 Aksi buat refresh
) {
    val uiData = when (status) {
        VerifStatus.PENDING -> StatusUI(R.drawable.jampasir_ijo, "Sedang Ditinjau", "Data sedang kami tinjau secara manual.\nSilakan klik tombol di bawah untuk cek status terbaru.", "Cek Status Terbaru")
        VerifStatus.SUCCESS -> StatusUI(R.drawable.centang_succes, "Verifikasi Berhasil", "Selamat! Dapur Anda telah aktif.\nSilakan lanjut ke Dashboard.", "Masuk ke Dashboard")
        VerifStatus.FAILED -> StatusUI(R.drawable.silang_merah, "Verifikasi Gagal", "Maaf, pendaftaran Anda ditolak.\nSilakan ajukan ulang berkas Anda.", "Ajukan Ulang")
    }

    Column(
        modifier = Modifier.fillMaxSize().background(White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(painter = painterResource(id = uiData.iconRes), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(32.dp))
        Text(uiData.title, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Gray900)
        Spacer(modifier = Modifier.height(12.dp))
        Text(uiData.desc, fontSize = 14.sp, color = Gray500, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = uiData.btnText,
            onClick = if (status == VerifStatus.PENDING) onRefreshClick else onNextClick,
            containerColor = FoundationGreen,
            modifier = Modifier.height(45.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}