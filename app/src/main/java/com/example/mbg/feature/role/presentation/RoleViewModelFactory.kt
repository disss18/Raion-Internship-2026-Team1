package com.example.mbg.feature.role.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbg.feature.auth.domain.AuthRepository

class RoleViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RoleViewModel(repository) as T
    }
}