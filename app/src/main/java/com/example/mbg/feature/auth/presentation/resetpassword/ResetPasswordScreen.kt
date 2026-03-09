package com.example.mbg.feature.auth.presentation.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.core.ui.component.textfield.PrimaryTextField
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.ui.theme.BlueNormal
import com.example.mbg.ui.theme.FoundationGreen
import com.example.mbg.ui.theme.poppins
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    onBack: () -> Unit,
    onResetSuccess: () -> Unit
) {

    val viewModel = remember {

        val remote = AuthRemoteDataSourceImpl()
        val repository = AuthRepositoryImpl(remote)

        ResetPasswordViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsState()

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    // ================= HANDLE SUCCESS =================

    LaunchedEffect(uiState.success) {

        if (uiState.success) {

            snackbarHostState.showSnackbar(
                "Password berhasil diperbarui"
            )

            onResetSuccess()
        }
    }

    // ================= HANDLE ERROR =================

    LaunchedEffect(uiState.error) {

        uiState.error?.let {

            snackbarHostState.showSnackbar(it)

            viewModel.clearError()
        }
    }

    Scaffold(

        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {

            TopAppBar(

                title = {
                    Text(
                        text = "Buat Kata Sandi Baru",
                        style = TextStyle(
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = FoundationGreen
                    )
                },

                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
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
                text = "Masukkan password yang baru",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(50.dp))

            Image(
                painter = painterResource(id = R.drawable.reset_password),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {

                Text("Kata Sandi")

                Spacer(modifier = Modifier.height(8.dp))

                PrimaryTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = "Masukkan Kata Sandi",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Konfirmasi Kata Sandi")

                Spacer(modifier = Modifier.height(8.dp))

                PrimaryTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = "Konfirmasi Kata Sandi",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(40.dp))

                PrimaryButton(
                    text = if (uiState.isLoading) "Loading..." else "Kirim",
                    containerColor = FoundationGreen,
                    enabled = !uiState.isLoading,
                    onClick = {

                        if (password.isBlank() || confirmPassword.isBlank()) {

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Password tidak boleh kosong"
                                )
                            }

                            return@PrimaryButton
                        }

                        if (password != confirmPassword) {

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    "Password tidak sama"
                                )
                            }

                            return@PrimaryButton
                        }

                        viewModel.resetPassword(password)
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}