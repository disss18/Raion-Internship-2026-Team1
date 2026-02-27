package com.example.mbg.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.auth.data.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl()

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(email: String, password: String) {

        viewModelScope.launch {

            _uiState.value = RegisterUiState(isLoading = true)

            val result = repository.register(email, password)

            _uiState.value = result.fold(
                onSuccess = {
                    RegisterUiState(isSuccess = true)
                },
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