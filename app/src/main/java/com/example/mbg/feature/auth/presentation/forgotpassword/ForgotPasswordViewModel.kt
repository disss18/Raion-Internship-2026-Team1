package com.example.mbg.feature.auth.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState

    fun sendResetEmail(email: String) {

        viewModelScope.launch {

            try {

                repository.sendResetPasswordEmail(email)

                _uiState.value = _uiState.value.copy(
                    emailSent = true
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    error = e.message
                )

            }

        }

    }

}