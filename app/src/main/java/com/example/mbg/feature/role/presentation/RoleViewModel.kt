package com.example.mbg.feature.role.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.domain.AuthRepository
import com.example.mbg.feature.role.domain.model.UserRole
import kotlinx.coroutines.launch

class RoleViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    fun saveRole(
        role: UserRole,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {

            val result = repository.updateUserRole(role.name)

            if (result.isSuccess) {
                onSuccess()
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
        }
    }
}