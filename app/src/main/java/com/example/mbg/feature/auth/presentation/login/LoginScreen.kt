package com.example.mbg.feature.auth.presentation.login

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.R
import com.example.mbg.core.ui.component.layout.AuthBackground
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.core.ui.component.textfield.PrimaryTextField
import com.example.mbg.feature.auth.component.AuthDivider
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.ui.theme.FoundationGreen
import com.example.mbg.ui.theme.poppins

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {

    val viewModel: LoginViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) onLoginSuccess()
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {

            AuthBackground()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(125.dp))

                // HEADER
                Text(
                    text = "Masuk ke Akun Anda",
                    style = TextStyle(
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    ),
                    color = FoundationGreen
                )

                Spacer(modifier = Modifier.height(120.dp))

                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(150.dp)
                        .graphicsLayer(
                            scaleX = 2.15f,
                            scaleY = 2.15f
                        ),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(1.dp))

                // FORM
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {

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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        ClickableText(
                            text = AnnotatedString("Lupa Kata Sandi?"),
                            onClick = { onNavigateToForgotPassword() },
                            style = TextStyle(
                                fontFamily = poppins,
                                fontWeight = FontWeight.Medium,
                                color = FoundationGreen
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    PrimaryButton(
                        text = if (uiState.isLoading) "Loading..." else "Masuk",
                        containerColor = FoundationGreen,
                        onClick = {
                            viewModel.login(
                                email.trim(),
                                password.trim()
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

                    Text("Belum punya akun? ")

                    ClickableText(
                        text = AnnotatedString("Daftar"),
                        onClick = { onNavigateToRegister() },
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