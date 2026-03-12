package com.example.mbg.feature.feedback.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedbackModel(

    val id: String? = null,

    @SerialName("school_name")
    val schoolName: String,

    @SerialName("parent_name")
    val parentName: String,

    val comment: String,

    val rating: Int,

    @SerialName("created_at")
    val createdAt: String? = null
)