package com.example.mbg.feature.auth.presentation.forgotpassword

data class ForgotPasswordUiState(

    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,

    val emailSent: Boolean = false,

    val error: String? = null

)