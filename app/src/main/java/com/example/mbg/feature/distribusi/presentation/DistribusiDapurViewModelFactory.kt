package com.example.mbg.feature.distribusi.presentation.dapur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbg.feature.distribusi.domain.repository.DistribusiRepository

class DistribusiDapurViewModelFactory(
    private val repository: DistribusiRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DistribusiDapurViewModel::class.java)) {
            return DistribusiDapurViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}