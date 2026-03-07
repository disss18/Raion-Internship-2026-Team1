package com.example.mbg.feature.auth.presentation.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ResetPasswordViewModel(
    private val repository: AuthRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState

    fun resetPassword(password: String) {

        viewModelScope.launch {

            _uiState.value = ResetPasswordUiState(isLoading = true)

            try {

                repository.updatePassword(password)
                repository.logout()

                _uiState.value = ResetPasswordUiState(
                    success = true
                )

            } catch (e: Exception) {

                _uiState.value = ResetPasswordUiState(
                    error = e.message ?: "Reset password gagal"
                )

            }

        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}