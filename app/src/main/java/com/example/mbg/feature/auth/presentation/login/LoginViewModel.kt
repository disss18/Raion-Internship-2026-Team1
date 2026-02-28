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

        // ================= VALIDATION =================
        val validationError = LoginValidator.validate(email, password)

        if (validationError != null) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = validationError
            )
            return
        }

        // ================= LOADING =================
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {

            val result = repository.login(email, password)

            _uiState.value = result.fold(
                onSuccess = {
                    LoginUiState(isSuccess = true)
                },
                onFailure = {
                    LoginUiState(
                        error = it.message ?: "Login gagal"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}