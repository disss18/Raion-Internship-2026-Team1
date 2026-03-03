package com.example.mbg.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {

        _uiState.value = LoginUiState(isLoading = true)

        viewModelScope.launch {

            val result = repository.login(email, password)

            _uiState.value = result.fold(
                onSuccess = {
                    LoginUiState()
                },
                onFailure = {
                    LoginUiState(
                        error = it.message ?: "Login gagal"
                    )
                }
            )
        }
    }

    fun loginWithGoogle() {

        _uiState.value = LoginUiState(isLoading = true)

        viewModelScope.launch {

            val result = repository.loginWithGoogle()

            _uiState.value = result.fold(
                onSuccess = {
                    LoginUiState()
                },
                onFailure = {
                    LoginUiState(
                        error = it.message ?: "Google login gagal"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}