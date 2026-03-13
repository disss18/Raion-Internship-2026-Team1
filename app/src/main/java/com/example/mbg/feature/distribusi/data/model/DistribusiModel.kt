package com.example.mbg.feature.distribusi.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DistribusiModel(
    @SerialName("id") val id: String = "",
    @SerialName("dapur_id") val dapurId: String = "",
    @SerialName("sekolah_id") val sekolahId: String = "",
    @SerialName("gizi_id") val giziId: String = "",
    @SerialName("tanggal") val tanggal: String = "",
    @SerialName("porsi") val porsi: Int = 0,
    @SerialName("status") val status: String = "diproses",
    @SerialName("estimasi_tiba") val estimasiTiba: String? = null,
    @SerialName("waktu_diproses") val waktuDiproses: String? = null,
    @SerialName("waktu_berangkat") val waktuBerangkat: String? = null,
    @SerialName("nama_kurir") val namaKurir: String? = null,
    @SerialName("telp_kurir") val telpKurir: String? = null,
    @SerialName("waktu_diterima") val waktuDiterima: String? = null,
    @SerialName("nama_penerima") val namaPenerima: String? = null,
    @SerialName("deskripsi_penerima") val deskripsiPenerima: String? = null,
    @SerialName("foto_bukti_url") val fotoBuktiUrl: String? = null
)