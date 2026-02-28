package com.example.mbg.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {

        val validationError = RegisterValidator.validate(
            name, email, password, confirmPassword
        )

        if (validationError != null) {
            _uiState.value = RegisterUiState(error = validationError)
            return
        }

        viewModelScope.launch {

            _uiState.value = RegisterUiState(isLoading = true)

            val result = repository.register(email, password)

            _uiState.value = result.fold(
                onSuccess = { RegisterUiState(isSuccess = true) },
                onFailure = {
                    RegisterUiState(error = it.message ?: "Register gagal")
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}