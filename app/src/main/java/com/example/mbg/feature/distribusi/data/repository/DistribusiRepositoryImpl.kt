package com.example.mbg.feature.distribusi.data.repository

import com.example.mbg.feature.distribusi.data.model.DistribusiModel
import com.example.mbg.feature.distribusi.domain.repository.DistribusiRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DistribusiRepositoryImpl(
    private val supabaseClient: SupabaseClient
) : DistribusiRepository {

    override suspend fun getDistribusiHariIni(dapurId: String, tanggal: String): Flow<Result<DistribusiModel?>> = flow {
        val result = supabaseClient.from("distribusi").select {
            filter {
                eq("dapur_id", dapurId)
                eq("tanggal", tanggal)
            }
        }.decodeSingleOrNull<DistribusiModel>()

        emit(Result.success(result))
    }.catch { e ->
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun getDistribusiSekolahHariIni(sekolahId: String, tanggal: String): Flow<Result<DistribusiModel?>> = flow {
        val result = supabaseClient.from("distribusi").select {
            filter {
                eq("sekolah_id", sekolahId)
                eq("tanggal", tanggal)
            }
        }.decodeSingleOrNull<DistribusiModel>()

        emit(Result.success(result))
    }.catch { e ->
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateStatusDiproses(
        distribusiId: String,
        waktuDiproses: String
    ): Result<Unit> = try {
        supabaseClient.from("distribusi").update({
            set("waktu_diproses", waktuDiproses)
        }) {
            filter { eq("id", distribusiId) }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateStatusDikirim(
        distribusiId: String,
        namaKurir: String,
        telpKurir: String,
        estimasiTiba: String,
        waktuBerangkat: String
    ): Result<Unit> = try {
        supabaseClient.from("distribusi").update({
            set("status", "dikirim")
            set("nama_kurir", namaKurir)
            set("telp_kurir", telpKurir)
            set("estimasi_tiba", estimasiTiba)
            set("waktu_berangkat", waktuBerangkat)
        }) {
            filter { eq("id", distribusiId) }
        }
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateStatusDiterima(
        distribusiId: String,
        namaPenerima: String,
        deskripsi: String,
        waktuDiterima: String,
        fotoBytes: ByteArray,
        fileName: String
    ): Result<Unit> = try {
        val bucket = supabaseClient.storage["bukti_distribusi"]
        val filePath = "distribusi/$distribusiId/$fileName"

        bucket.upload(path = filePath, data = fotoBytes)

        val fotoUrl = bucket.publicUrl(filePath)

        supabaseClient.from("distribusi").update({
            set("status", "diterima")
            set("nama_penerima", namaPenerima)
            set("deskripsi_penerima", deskripsi)
            set("waktu_diterima", waktuDiterima)
            set("foto_bukti_url", fotoUrl)
        }) {
            filter { eq("id", distribusiId) }
        }

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}