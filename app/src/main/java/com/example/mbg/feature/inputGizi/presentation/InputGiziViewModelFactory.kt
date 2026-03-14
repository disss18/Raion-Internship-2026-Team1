package com.example.mbg.feature.inputGizi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbg.feature.inputGizi.data.repository.InputGiziRepositoryImpl

class InputGiziViewModelFactory(
    private val repository: InputGiziRepositoryImpl
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(InputGiziViewModel::class.java)) {
            return InputGiziViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}