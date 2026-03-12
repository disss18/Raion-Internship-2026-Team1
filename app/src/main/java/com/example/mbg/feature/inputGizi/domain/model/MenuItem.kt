package com.example.mbg.feature.inputGizi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: String = "",
    val dapur_id: String,
    val nama_item: String,
    val foto_url: String? = null,
    val berat_gram: Double,
    val kalori: Double,
    val karbohidrat: Double,
    val protein: Double,
    val lemak: Double,
    val created_at: String = ""
)