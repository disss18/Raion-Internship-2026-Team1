package com.example.mbg.feature.inputGizi.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.inputGizi.data.repository.InputGiziRepositoryImpl
import com.example.mbg.feature.inputGizi.domain.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class InputGiziViewModel(private val repository: InputGiziRepositoryImpl) : ViewModel() {

    private val _katalogMenu = MutableStateFlow<List<MenuItem>>(emptyList())
    val katalogMenu: StateFlow<List<MenuItem>> = _katalogMenu.asStateFlow()

    private val _selectedItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val selectedItems: StateFlow<List<MenuItem>> = _selectedItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _totalKalori = MutableStateFlow(0.0)
    val totalKalori: StateFlow<Double> = _totalKalori

    private val _totalProtein = MutableStateFlow(0.0)
    val totalProtein: StateFlow<Double> = _totalProtein

    private val _totalKarbo = MutableStateFlow(0.0)
    val totalKarbo: StateFlow<Double> = _totalKarbo

    private val _totalLemak = MutableStateFlow(0.0)
    val totalLemak: StateFlow<Double> = _totalLemak

    init {
        fetchKatalogMenu()
    }

    fun fetchKatalogMenu() {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                val items = repository.getMenuItems()

                _katalogMenu.value = items

            } catch (e: Exception) {

                e.printStackTrace()

                // fallback supaya UI tidak crash
                _katalogMenu.value = emptyList()

            } finally {

                _isLoading.value = false

            }
        }
    }

    fun toggleItemSelection(item: MenuItem, isChecked: Boolean) {
        val currentList = _selectedItems.value.toMutableList()
        if (isChecked) {
            if (!currentList.any { it.id == item.id }) currentList.add(item)
        } else {
            currentList.removeAll { it.id == item.id }
        }
        _selectedItems.value = currentList
        hitungAkumulasiGizi(currentList)
    }

    private fun hitungAkumulasiGizi(items: List<MenuItem>) {

        _totalKalori.value = items.sumOf { it.kalori }

        _totalProtein.value = items.sumOf { it.protein }

        _totalKarbo.value = items.sumOf { it.karbohidrat }

        _totalLemak.value = items.sumOf { it.lemak }

    }

    fun publishMenuHariIni(onSuccess: () -> Unit) {
        onSuccess()
    }

    fun tambahMenuKeDatabase(
        context: Context,
        kategori: String,
        namaMenu: String,
        satuanPorsi: String,
        jumlahPorsi: String,
        giziList: List<GiziItem>,
        deskripsi: String,
        imageUri: Uri,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                var vKalori = 0.0
                var vProtein = 0.0
                var vKarbo = 0.0
                var vLemak = 0.0

                giziList.forEach { gizi ->
                    val jumlah = gizi.jumlah.toDoubleOrNull() ?: 0.0
                    when (gizi.jenis.lowercase()) {
                        "kalori" -> vKalori = jumlah
                        "protein" -> vProtein = jumlah
                        "karbohidrat" -> vKarbo = jumlah
                        "lemak", "serat" -> vLemak = jumlah
                    }
                }

                val file = uriToFile(context, imageUri)
                val fotoUrl = repository.uploadFotoItem(file, "foto_${System.currentTimeMillis()}.jpg")

                val newItem = MenuItem(
                    id = UUID.randomUUID().toString(),
                    // TODO: Ini masih pakai ID dummy buat testing karena data akun belum ada.
                    // Nanti wajib diganti narik ID otomatis kalau fitur Login udah digabung.
                    dapur_id = "00000000-0000-0000-0000-000000000000",
                    nama_item = namaMenu,
                    foto_url = fotoUrl,
                    berat_gram = jumlahPorsi.toDoubleOrNull() ?: 0.0,
                    kalori = vKalori,
                    karbohidrat = vKarbo,
                    protein = vProtein,
                    lemak = vLemak,
                    created_at = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
                )

                repository.insertMenuItem(newItem)
                fetchKatalogMenu()

                _isLoading.value = false
                onSuccess()

            } catch (e: Exception) {
                _isLoading.value = false
                onError(e.message ?: "Kesalahan Server Supabase")
                e.printStackTrace()
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile
    }
}