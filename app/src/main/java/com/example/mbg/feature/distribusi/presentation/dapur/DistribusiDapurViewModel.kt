package com.example.mbg.feature.distribusi.presentation.dapur

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.distribusi.domain.repository.DistribusiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DistribusiDapurState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val currentStatus: String = "diproses",
    val namaKurir: String = "",
    val telpKurir: String = "",
    val namaPenerima: String = "",
    val deskripsiPenerima: String = "",
    val fotoBukti: ByteArray? = null
)

class DistribusiDapurViewModel(
    private val repository: DistribusiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DistribusiDapurState())
    val state: StateFlow<DistribusiDapurState> = _state.asStateFlow()

    // 🔥 FUNGSI BARU: BIAR SINKRON SAMA DATABASE SAAT APLIKASI DIBUKA
    fun fetchStatusAwal(dapurId: String) {
        viewModelScope.launch {
            val hariIni = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.getDistribusiHariIni(dapurId, hariIni).collect { result ->
                result.onSuccess { model ->
                    if (model != null) {
                        _state.update { it.copy(currentStatus = model.status ?: "diproses") }
                    }
                }
            }
        }
    }

    fun onNamaKurirChange(newValue: String) = _state.update { it.copy(namaKurir = newValue) }
    fun onTelpKurirChange(newValue: String) = _state.update { it.copy(telpKurir = newValue) }
    fun onNamaPenerimaChange(newValue: String) = _state.update { it.copy(namaPenerima = newValue) }
    fun onDeskripsiPenerimaChange(newValue: String) = _state.update { it.copy(deskripsiPenerima = newValue) }
    fun onFotoBuktiChange(newValue: ByteArray?) = _state.update { it.copy(fotoBukti = newValue) }

    fun dismissSuccessDialog() {
        _state.update { it.copy(isSuccess = false) }
    }

    fun konfirmasiStatus(distribusiId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            val currentTime = getCurrentTimestamp()
            val result = when (_state.value.currentStatus) {
                "diproses" -> {
                    repository.updateStatusDiproses(distribusiId, currentTime)
                }
                "dikirim" -> {
                    repository.updateStatusDikirim(
                        distribusiId = distribusiId,
                        namaKurir = _state.value.namaKurir,
                        telpKurir = _state.value.telpKurir,
                        estimasiTiba = "12:15 - 12:45 WIB",
                        waktuBerangkat = currentTime
                    )
                }
                "diterima" -> {
                    val fotoBytes = _state.value.fotoBukti
                    if (fotoBytes == null) {
                        _state.update { it.copy(isLoading = false, errorMessage = "Foto bukti wajib diupload") }
                        return@launch
                    }
                    repository.updateStatusDiterima(
                        distribusiId = distribusiId,
                        namaPenerima = _state.value.namaPenerima,
                        deskripsi = _state.value.deskripsiPenerima,
                        waktuDiterima = currentTime,
                        fotoBytes = fotoBytes,
                        fileName = "bukti_${System.currentTimeMillis()}.jpg"
                    )
                }
                else -> Result.failure(Exception("Status tidak valid"))
            }

            result.fold(
                onSuccess = {
                    _state.update {
                        val nextStatus = when (it.currentStatus) {
                            "diproses" -> "dikirim"
                            "dikirim" -> "diterima"
                            else -> "diterima"
                        }
                        it.copy(isLoading = false, isSuccess = true, currentStatus = nextStatus)
                    }
                },
                onFailure = { error ->
                    _state.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
            )
        }
    }

    private fun getCurrentTimestamp(): String {
        // 🔥 FORMAT WAKTU UDAH DIBENERIN BIAR SUPABASE GAK NANGIS LAGI
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        return sdf.format(Date())
    }
}