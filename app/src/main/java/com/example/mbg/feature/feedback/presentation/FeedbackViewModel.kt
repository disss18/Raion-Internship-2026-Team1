package com.example.mbg.feature.feedback.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.feedback.data.FeedbackRepository
import com.example.mbg.feature.feedback.domain.model.FeedbackModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedbackViewModel(
    private val repository: FeedbackRepository = FeedbackRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState: StateFlow<FeedbackUiState> = _uiState

    private val pageSize = 10L

    init {
        refresh()
    }

    fun refresh() {

        viewModelScope.launch {

            try {

                val feedbackList = repository.getFeedback(
                    limit = pageSize,
                    offset = 0
                )

                println("VIEWMODEL FEEDBACK SIZE = ${feedbackList.size}")

                val summary =
                    repository.getRatingSummary(feedbackList)

                _uiState.update { current ->

                    current.copy(
                        feedbackList = feedbackList,
                        ratingAverage = summary.first,
                        ratingDistribution = summary.second,
                        totalReview = feedbackList.size,
                        isLoading = false,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {

                e.printStackTrace()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun sendFeedback(
        school: String,
        parent: String,
        comment: String,
        rating: Int
    ) {

        viewModelScope.launch {

            try {

                repository.insertFeedback(
                    FeedbackModel(
                        schoolName = school,
                        parentName = parent,
                        comment = comment,
                        rating = rating
                    )
                )

                refresh()

            } catch (e: Exception) {

                e.printStackTrace()

                _uiState.update {
                    it.copy(errorMessage = e.message)
                }
            }
        }
    }
}