package com.example.mbg.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.ui.theme.*
@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,    // buat login
    onNavigateToRegister: () -> Unit  // buat daftar
) {


    // gradien background
    val gradientBrush = Brush.verticalGradient(
        0.0f to BlueLightHover,
        0.45f to BlueLightHover,
        1.0f to BlueNormal
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // logo
            Image(
                painter = painterResource(id = R.drawable.logo_mbg),
                contentDescription = "Logo MBG+",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // teks MBG+
            Text(
                text = "MBG +",
                fontFamily = poppins,
                fontSize = 60.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlueNormal
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Platform monitoring program\nmakan bergizi untuk sekolah",
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = BlueNormalHover,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1.5f))

            // Tombol masuk
            Button(
                onClick = onNavigateToLogin,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BlueDark
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.masuk_icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Masuk",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // tombol daftar
            Button(
                onClick = onNavigateToRegister,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BlueDark
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.daftar_icon),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Daftar",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {

}