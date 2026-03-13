package com.example.mbg.feature.distribusi.presentation.sekolah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.distribusi.data.model.DistribusiModel
import com.example.mbg.feature.distribusi.domain.repository.DistribusiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DistribusiSekolahState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: DistribusiModel = DistribusiModel()
)

class DistribusiSekolahViewModel(
    private val repository: DistribusiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DistribusiSekolahState())
    val state: StateFlow<DistribusiSekolahState> = _state.asStateFlow()

    // BACA DATA DARI SUPABASE BENERAN
    fun fetchDistribusi(sekolahId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val hariIni = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            repository.getDistribusiSekolahHariIni(sekolahId, hariIni).collect { result ->
                result.fold(
                    onSuccess = { model ->
                        if (model != null) {
                            _state.update { it.copy(isLoading = false, data = model) }
                        } else {
                            _state.update { it.copy(isLoading = false, errorMessage = "Data distribusi hari ini belum tersedia.") }
                        }
                    },
                    onFailure = { error ->
                        _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                    }
                )
            }
        }
    }
}