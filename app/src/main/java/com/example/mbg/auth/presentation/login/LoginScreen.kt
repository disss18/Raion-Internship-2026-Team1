package com.example.mbg.auth.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mbg.auth.component.AuthDivider
import com.example.mbg.core.ui.component.AuthBackground
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.core.ui.component.PrimaryTextField
import com.example.mbg.ui.theme.BlueLight
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,

) {
    val viewModel: LoginViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Observe success (one-time navigation)
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
        }
    }

    AuthBackground {

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(80.dp))

                Text(
                    text = "Masuk ke Akun Anda",
                    style = MaterialTheme.typography.titleMedium,
                    color = BlueNormal,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.align(Alignment.Start)
                )

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

                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Masukkan Kata Sandi",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Loading..." else "Masuk",
                    containerColor = BlueNormal,
                    onClick = {
                        viewModel.login(email, password)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AuthDivider()

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Lanjutkan dengan Google",
                    onClick = { },
                    containerColor = BlueLight,
                    contentColor = BlueNormal,
                    borderColor = BlueNormal
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text("Belum punya akun? ")

                    ClickableText(
                        text = AnnotatedString("ajukan akses"),
                        onClick = { onNavigateToRegister() }
                    )
                }
            }

            // ERROR UI
            uiState.error?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )

                LaunchedEffect(message) {
                    viewModel.clearError()
                }
            }
        }
    }
}