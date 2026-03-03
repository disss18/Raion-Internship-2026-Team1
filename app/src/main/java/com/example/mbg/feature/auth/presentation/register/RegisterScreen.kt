package com.example.mbg.feature.auth.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mbg.core.ui.component.AuthBackground
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.core.ui.component.PrimaryTextField
import com.example.mbg.feature.auth.component.AuthDivider
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSource
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.ui.theme.BlueLight
import com.example.mbg.ui.theme.BlueNormal
import com.example.mbg.ui.theme.poppins

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    val viewModel = remember {
        val remote = AuthRemoteDataSource()
        val repository = AuthRepositoryImpl(remote)
        RegisterViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // ================= SUCCESS =================
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
        }
    }

    // ================= ERROR =================
    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // ================= BACKGROUND =================
            AuthBackground(
                waveOffsetY = (-40).dp
            )

            // ================= CONTENT =================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(130.dp))

                Text(
                    text = "Registrasi Akun",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    color = BlueNormal
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(0.90f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // ===== NAMA =====
                    Text("Nama", modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Masukkan nama"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ===== EMAIL =====
                    Text("Email", modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Masukkan email"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ===== PASSWORD =====
                    Text("Kata Sandi", modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Masukkan kata sandi",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // ===== CONFIRM PASSWORD =====
                    Text("Konfirmasi Kata Sandi", modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    PrimaryTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Konfirmasi kata sandi",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // ===== REGISTER BUTTON =====
                    PrimaryButton(
                        text = if (uiState.isLoading) "Loading..." else "Daftar",
                        containerColor = BlueNormal,
                        enabled = !uiState.isLoading,
                        onClick = {
                            viewModel.register(
                                name.trim(),
                                email.trim(),
                                password.trim(),
                                confirmPassword.trim()
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AuthDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    PrimaryButton(
                        text = "Lanjutkan dengan Google",
                        onClick = { viewModel.loginWithGoogle() },
                        containerColor = BlueLight,
                        contentColor = BlueNormal,
                        borderColor = BlueNormal
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Sudah punya akun? ")
                    ClickableText(
                        text = AnnotatedString("Masuk"),
                        onClick = { onNavigateToLogin() },
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = BlueNormal
                        )
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}