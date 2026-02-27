package com.example.mbg.auth.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.mbg.auth.component.AuthDivider
import com.example.mbg.core.ui.component.AuthBackground
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.core.ui.component.PrimaryTextField
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    AuthBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(120.dp))

            Text(
                text = "Registrasi Akun Anda",
                style = MaterialTheme.typography.titleMedium,
                color = BlueNormal,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Nama
            Text(
                text = "Nama",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Masukkan nama"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Text(
                text = "Email",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Masukkan email"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            Text(
                text = "Kata Sandi",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Masukkan Kata Sandi",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Konfirmasi Password
            Text(
                text = "Konfirmasi Kata Sandi",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Konfirmasi Kata Sandi",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            PrimaryButton(
                text = "Daftar",
                containerColor = BlueNormal,
                onClick = {
                    // nanti pindahkan ke ViewModel
                    onRegisterSuccess()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AuthDivider()

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Lanjutkan dengan Google",
                onClick = {},
                containerColor = Color.White,
                contentColor = BlueNormal,
                borderColor = BlueNormal
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text("Sudah punya akun? ")

                ClickableText(
                    text = AnnotatedString("Masuk"),
                    onClick = { onNavigateBack() }
                )
            }
        }
    }
}