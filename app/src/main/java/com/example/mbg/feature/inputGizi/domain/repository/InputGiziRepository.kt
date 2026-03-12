package com.example.mbg.feature.inputGizi.domain.repository

import com.example.mbg.feature.inputGizi.domain.model.MenuItem
import com.example.mbg.feature.inputGizi.domain.model.DailyPublishedMenu
import java.io.File

interface InputGiziRepository {
    // 1. Aksi untuk Form Tambah Item (Katalog)
    suspend fun uploadFotoItem(file: File, fileName: String): String
    suspend fun getMenuItems(): List<MenuItem>
    suspend fun insertMenuItem(item: MenuItem)

    // 2. Aksi untuk Layar Tambah Menu Hari Ini (Checklist)
    suspend fun getKatalogMenu(dapurId: String): List<MenuItem>

    // 3. Aksi untuk Akumulasi Akhir
    suspend fun publishMenuHariIni(dailyMenu: DailyPublishedMenu)
}