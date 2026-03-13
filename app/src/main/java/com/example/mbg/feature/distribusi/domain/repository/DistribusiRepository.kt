package com.example.mbg.feature.distribusi.domain.repository

import com.example.mbg.feature.distribusi.data.model.DistribusiModel
import kotlinx.coroutines.flow.Flow

interface DistribusiRepository {
    suspend fun getDistribusiHariIni(dapurId: String, tanggal: String): Flow<Result<DistribusiModel?>>

    suspend fun getDistribusiSekolahHariIni(sekolahId: String, tanggal: String): Flow<Result<DistribusiModel?>>

    suspend fun updateStatusDiproses(
        distribusiId: String,
        waktuDiproses: String
    ): Result<Unit>

    suspend fun updateStatusDikirim(
        distribusiId: String,
        namaKurir: String,
        telpKurir: String,
        estimasiTiba: String,
        waktuBerangkat: String
    ): Result<Unit>

    suspend fun updateStatusDiterima(
        distribusiId: String,
        namaPenerima: String,
        deskripsi: String,
        waktuDiterima: String,
        fotoBytes: ByteArray,
        fileName: String
    ): Result<Unit>
}