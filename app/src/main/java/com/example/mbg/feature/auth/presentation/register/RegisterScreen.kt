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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.feature.auth.component.AuthDivider
import com.example.mbg.core.ui.component.AuthBackground
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.core.ui.component.PrimaryTextField
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {

    val viewModel: RegisterViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onRegisterSuccess()
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        AuthBackground(
            modifier = Modifier.padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Registrasi Akun",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueNormal
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text("Nama", modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Masukkan nama"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Email", modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Masukkan email"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Kata Sandi", modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Masukkan kata sandi",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Konfirmasi Kata Sandi", modifier = Modifier.align(Alignment.Start))
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Konfirmasi kata sandi",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Loading..." else "Daftar",
                    containerColor = BlueNormal,
                    onClick = {
                        viewModel.register(
                            name.trim(),
                            email.trim(),
                            password.trim(),
                            confirmPassword.trim()
                        )
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AuthDivider()

                Spacer(modifier = Modifier.height(24.dp))

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
}