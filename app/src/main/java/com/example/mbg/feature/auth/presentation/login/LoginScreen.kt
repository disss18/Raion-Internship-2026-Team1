package com.example.mbg.feature.auth.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mbg.R
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
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val viewModel = remember {
        val remote = AuthRemoteDataSource()
        val repository = AuthRepositoryImpl(remote)
        LoginViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // ================= SUCCESS =================
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
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
        AuthBackground(
            modifier = Modifier.padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                // jarak dari atas
                Spacer(modifier = Modifier.height(90.dp))

                Text(
                    text = "Masuk ke Akun Anda",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    color = BlueNormal
                )

                Spacer(modifier = Modifier.height(70.dp))

                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(150.dp)
                        .offset(y = 20.dp)
                        .graphicsLayer(
                            scaleX = 2.0f, // 1.0 normal
                            scaleY = 2.0f
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.90f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

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

                    Spacer(modifier = Modifier.height(24.dp))

                    // ===== LOGIN BUTTON =====
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

                    Spacer(modifier = Modifier.height(10.dp))

                    AuthDivider()

                    Spacer(modifier = Modifier.height(10.dp))

                    PrimaryButton(
                        text = "Lanjutkan dengan Google",
                        onClick = { /* TODO */ },
                        containerColor = BlueLight,
                        contentColor = BlueNormal,
                        borderColor = BlueNormal
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                // ===== REGISTER TEXT =====
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Belum punya akun? ")
                    ClickableText(
                        text = AnnotatedString("Daftar"),
                        onClick = { onNavigateToRegister() },
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