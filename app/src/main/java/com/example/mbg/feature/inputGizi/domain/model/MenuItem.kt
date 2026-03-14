package com.example.mbg.feature.inputGizi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: String = "",
    val dapur_id: String = "",
    val nama_item: String = "",
    val foto_url: String? = null,
    val berat_gram: Double = 0.0,
    val kalori: Double = 0.0,
    val karbohidrat: Double = 0.0,
    val protein: Double = 0.0,
    val lemak: Double = 0.0,
    val created_at: String? = null
)