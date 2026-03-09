package com.example.mbg.feature.verificationMBG.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.ui.theme.*

// 1. Enum status
enum class VerifStatus { PENDING, SUCCESS, FAILED }

// 2. Data Class biar Android Studio ga mabuk ngebaca tipe data
data class StatusUI(
    val iconRes: Int,
    val title: String,
    val desc: String,
    val btnText: String,
    val btnColor: Color
)

@Composable
fun VerificationStatusScreen(
    status: VerifStatus,
    onNextClick: () -> Unit = {}
) {
    // 3. Panggil datanya pake Data Class yang udah dibikin
    val uiData = when (status) {
        VerifStatus.PENDING -> StatusUI(
            iconRes = R.drawable.jampasir_ijo,
            title = "Verifikasi Berhasil Dikirim",
            desc = "Data sedang kami tinjau. Update\nakan dikirim ke email Anda.",
            btnText = "Kembali ke Beranda",
            btnColor = FoundationGreen
        )
        VerifStatus.SUCCESS -> StatusUI(
            iconRes = R.drawable.centang_succes,
            title = "Verifikasi Berhasil",
            desc = "Akun kamu telah berhasil diverifikasi.\nSekarang kamu dapat melanjutkan\nmenggunakan layanan.",
            btnText = "Selanjutnya",
            btnColor = FoundationGreen
        )
        VerifStatus.FAILED -> StatusUI(
            iconRes = R.drawable.silang_merah,
            title = "Verifikasi Gagal",
            desc = "Maaf, pengajuan verifikasi Dapur MBG\nbelum dapat disetujui.\n\nSilakan periksa kembali dokumen yang\ndiunggah dan ajukan verifikasi ulang.",
            btnText = "Ajukan Ulang",
            btnColor = FoundationGreen
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = uiData.iconRes),
            contentDescription = "Status Icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = uiData.title,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Gray900,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = uiData.desc,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Gray500,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        // Spacer buat ngedorong tombol ke paling bawah
        Spacer(modifier = Modifier.weight(1f))

        // tombol tampil selain pending
        if (status != VerifStatus.PENDING) {
            PrimaryButton(
                text = uiData.btnText,
                onClick = onNextClick,
                containerColor = uiData.btnColor,
                modifier = Modifier.height(35.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ==========================================
// PREVIEW
// ==========================================
@Preview(showBackground = true, name = "1. Pending Status")
@Composable
fun PreviewPendingStatus() {
    MBGTheme { VerificationStatusScreen(status = VerifStatus.PENDING) }
}

@Preview(showBackground = true, name = "2. Success Status")
@Composable
fun PreviewSuccessStatus() {
    MBGTheme { VerificationStatusScreen(status = VerifStatus.SUCCESS) }
}

@Preview(showBackground = true, name = "3. Failed Status")
@Composable
fun PreviewFailedStatus() {
    MBGTheme { VerificationStatusScreen(status = VerifStatus.FAILED) }
}