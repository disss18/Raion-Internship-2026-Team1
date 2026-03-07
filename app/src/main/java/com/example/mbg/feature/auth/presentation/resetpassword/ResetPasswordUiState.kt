package com.example.mbg.feature.auth.presentation.resetpassword

data class ResetPasswordUiState(

    val isLoading: Boolean = false,

    val success: Boolean = false,

    val error: String? = null

)