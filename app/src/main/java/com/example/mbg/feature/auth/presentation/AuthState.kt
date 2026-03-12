package com.example.mbg.feature.auth.presentation

sealed class AuthState {

    object Loading : AuthState()

    object Unauthenticated : AuthState()

    object Authenticated : AuthState()

    object NeedRole : AuthState()
}