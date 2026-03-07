package com.example.mbg.feature.auth.presentation.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.core.ui.component.textfield.PrimaryTextField
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.ui.theme.BlueNormal
import com.example.mbg.ui.theme.poppins
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit
) {

    val viewModel = remember {

        val remote = AuthRemoteDataSourceImpl()

        val repository = AuthRepositoryImpl(remote)

        ForgotPasswordViewModel(repository)

    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    var email by remember { mutableStateOf("") }

    // ================= SUCCESS =================

    LaunchedEffect(uiState.emailSent) {

        if (uiState.emailSent) {

            snackbarHostState.showSnackbar(
                "Email reset password berhasil dikirim"
            )

        }

    }

    // ================= ERROR =================

    LaunchedEffect(uiState.error) {

        uiState.error?.let {

            snackbarHostState.showSnackbar(it)

        }

    }

    Scaffold(

        snackbarHost = { SnackbarHost(snackbarHostState) },

        // ================= TOP BAR =================

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Lupa Kata Sandi",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = BlueNormal
                    )

                },

                navigationIcon = {

                    IconButton(onClick = onBackToLogin) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Masukkan emailmu. Link reset password akan dikirim.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.drawable.reset_password),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Email")

                Spacer(modifier = Modifier.height(8.dp))

                PrimaryTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Masukkan email"
                )

                Spacer(modifier = Modifier.height(40.dp))

                PrimaryButton(
                    text = "Kirim",
                    containerColor = BlueNormal,
                    onClick = {

                        viewModel.sendResetEmail(
                            email.trim()
                        )

                    }
                )

            }

        }

    }

}