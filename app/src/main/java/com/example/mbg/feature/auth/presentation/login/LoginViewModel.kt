package com.example.mbg.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.data.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {

        val validationError = LoginValidator.validate(email, password)

        if (validationError != null) {
            _uiState.value = LoginUiState(error = validationError)
            return
        }

        viewModelScope.launch {

            _uiState.value = LoginUiState(isLoading = true)

            val result = repository.login(email, password)

            _uiState.value = result.fold(
                onSuccess = { LoginUiState(isSuccess = true) },
                onFailure = {
                    LoginUiState(error = it.message ?: "Login gagal")
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}