package com.example.mbg.feature.splashscreen.components // Sesuaikan package Anda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mbg.R
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.ui.theme.FoundationGreen
import com.example.mbg.ui.theme.GreenChip
import com.example.mbg.ui.theme.GreenLight
import com.example.mbg.ui.theme.White

@Composable
fun WelcomeScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {

    val gradientBackground = Brush.verticalGradient(
        colorStops = arrayOf(
            0.0f to GreenLight,
            0.35f to FoundationGreen
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
    ) {
            //teks sama logo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_ijo1),
                contentDescription = "Logo MBG",
                modifier = Modifier.size(110.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Platform monitoring program" +
                        "\nmakan bergizi untuk sekolah",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {

            // gambar kurir
            Image(
                painter = painterResource(id = R.drawable.kurir_icon),
                contentDescription = "Ilustrasi Kurir",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .offset(y = (-203).dp),
                contentScale = ContentScale.Fit
            )

            //gelombang
            Image(
                painter = painterResource(id = R.drawable.gelombang_welcome),
                contentDescription = "Background Gelombang",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillWidth
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                PrimaryButton(
                    text = "Masuk",
                    onClick = onNavigateToLogin,
                    containerColor = FoundationGreen,
                    contentColor = White,
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryButton(
                    text = "Daftar",
                    onClick = onNavigateToRegister,
                    containerColor = GreenChip,
                    contentColor = FoundationGreen,
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}


