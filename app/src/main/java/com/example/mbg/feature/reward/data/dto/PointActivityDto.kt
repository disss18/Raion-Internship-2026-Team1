package com.example.mbg.feature.reward.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PointActivityDto(

    val id: String? = null,

    val user_id: String,

    val activity_type: String,

    val point: Int,

    val created_at: String? = null
)