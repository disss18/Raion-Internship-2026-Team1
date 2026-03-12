package com.example.mbg.feature.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.core.ui.component.layout.AuthBackground
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.core.ui.component.textfield.PrimaryTextField
import com.example.mbg.feature.auth.component.AuthDivider
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.ui.theme.FoundationGreen
import com.example.mbg.ui.theme.poppins

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    val viewModel: RegisterViewModel = viewModel()

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
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {

            // Background ikut scroll (sama seperti LoginScreen)
            AuthBackground()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(125.dp))

                Text(
                    text = "Registrasi Akun Anda",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    color = FoundationGreen
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {

                    Text("Nama")
                    Spacer(modifier = Modifier.height(4.dp))

                    PrimaryTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = "Masukkan nama"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Email")
                    Spacer(modifier = Modifier.height(4.dp))

                    PrimaryTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Masukkan email"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Kata Sandi")
                    Spacer(modifier = Modifier.height(4.dp))

                    PrimaryTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Masukkan kata sandi",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Konfirmasi Kata Sandi")
                    Spacer(modifier = Modifier.height(4.dp))

                    PrimaryTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = "Konfirmasi kata sandi",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = if (uiState.isLoading) "Loading..." else "Masuk",
                        containerColor = FoundationGreen,
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

                    Spacer(modifier = Modifier.height(8.dp))

                    AuthDivider()

                    Spacer(modifier = Modifier.height(8.dp))

                    PrimaryButton(
                        text = "Lanjutkan dengan Google",
                        onClick = { viewModel.loginWithGoogle() },
                        containerColor = Color(0xFFEDEFEF),
                        contentColor = FoundationGreen,
                        borderColor = FoundationGreen
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                            color = FoundationGreen
                        )
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}