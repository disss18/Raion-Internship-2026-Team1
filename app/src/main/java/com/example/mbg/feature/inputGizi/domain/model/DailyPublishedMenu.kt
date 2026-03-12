package com.example.mbg.feature.inputGizi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyPublishedMenu(
    val id: String = "",
    val dapur_id: String,
    val tanggal: String, // Format YYYY-MM-DD
    val item_ids: List<String>, // Array UUID dari menu yang diceklis
    val total_kalori: Double,
    val total_karbohidrat: Double,
    val total_protein: Double,
    val total_lemak: Double
)