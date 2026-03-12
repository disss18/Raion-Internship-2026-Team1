package com.example.mbg.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.di.AuthModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthModule.repository

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {

        _uiState.value = LoginUiState(isLoading = true)

        viewModelScope.launch {

            val result = repository.login(email, password)

            _uiState.value = result.fold(

                onSuccess = {
                    LoginUiState(isSuccess = true)
                },

                onFailure = {
                    LoginUiState(error = it.message ?: "Login gagal")
                }
            )
        }
    }

    fun loginWithGoogle() {

        viewModelScope.launch {

            try {
                repository.loginWithGoogle()
            } catch (e: Exception) {

                _uiState.value = LoginUiState(
                    error = e.message ?: "Google login gagal"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}