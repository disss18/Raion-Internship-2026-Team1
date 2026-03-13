package com.example.mbg.feature.reward.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserPointDto(

    val user_id: String,

    val total_point: Int,

    val updated_at: String? = null

)