package com.example.mbg.feature.inputGizi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItemInsert(
    val id: String,
    val dapur_id: String,
    val nama_item: String,
    val foto_url: String?,
    val berat_gram: Double,
    val kalori: Double,
    val karbohidrat: Double,
    val protein: Double,
    val lemak: Double,
    val created_at: String?
)