package com.example.mbg.feature.feedback.presentation

import com.example.mbg.feature.feedback.domain.model.FeedbackModel

data class FeedbackUiState(
    val feedbackList: List<FeedbackModel> = emptyList(),
    val ratingAverage: Double = 0.0,
    val totalReview: Int = 0,
    val ratingDistribution: Map<Int, Int> = emptyMap(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val isSubmitting: Boolean = false,
    val endReached: Boolean = false,
    val errorMessage: String? = null
)