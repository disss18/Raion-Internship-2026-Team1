package com.example.mbg.feature.allergy.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllergyModel(

    val id: String? = null,

    @SerialName("school_name")
    val schoolName: String,

    @SerialName("allergy_name")
    val allergyName: String,

    @SerialName("total_student")
    val totalStudent: Int,

    @SerialName("created_at")
    val createdAt: String? = null
)