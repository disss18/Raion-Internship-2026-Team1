package com.example.mbg.feature.mbg.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MBGNeedModel(

    val id: String? = null,

    @SerialName("school_name")
    val schoolName: String,

    @SerialName("total_student")
    val totalStudent: Int,

    @SerialName("created_at")
    val createdAt: String? = null
)