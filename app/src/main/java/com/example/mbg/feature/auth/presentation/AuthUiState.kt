package com.example.mbg.feature.auth.presentation

data class AuthUiState(

    val isLoading: Boolean = true,

    val isAuthenticated: Boolean = false,

    val role: String? = null,

    val verificationStatus: String? = null
)