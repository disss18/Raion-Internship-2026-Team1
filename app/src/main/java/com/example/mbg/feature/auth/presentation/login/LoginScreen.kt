package com.example.mbg.feature.auth.presentation.login

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
import com.example.mbg.ui.theme.BlueLight
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
) {

    val viewModel: LoginViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Navigate once on success
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
        }
    }

    // Show error
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
                    text = "Masuk ke Akun Anda",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueNormal
                )

                Spacer(modifier = Modifier.height(32.dp))

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

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Loading..." else "Masuk",
                    containerColor = BlueNormal,
                    onClick = {
                        viewModel.login(
                            email.trim(),
                            password.trim()
                        )
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                AuthDivider()

                Spacer(modifier = Modifier.height(24.dp))

                PrimaryButton(
                    text = "Lanjutkan dengan Google",
                    onClick = {},
                    containerColor = BlueLight,
                    contentColor = BlueNormal,
                    borderColor = BlueNormal
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Text("Belum punya akun? ")
                    ClickableText(
                        text = AnnotatedString("Daftar"),
                        onClick = {onNavigateToRegister()}
                    )
                }
            }
        }
    }
}