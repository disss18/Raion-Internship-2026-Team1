package com.example.mbg.feature.feedback.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.feedback.data.FeedbackRepository
import com.example.mbg.feature.feedback.domain.model.FeedbackModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedbackViewModel(
    private val repository: FeedbackRepository = FeedbackRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState: StateFlow<FeedbackUiState> = _uiState

    private val pageSize = 10L

    init {
        refresh()
    }

    fun loadFeedback() {

        val currentState = _uiState.value
        if (currentState.isLoading || currentState.endReached) return

        val isFirstLoad = currentState.feedbackList.isEmpty()

        _uiState.update {
            it.copy(
                isLoading = isFirstLoad,
                isLoadingMore = !isFirstLoad
            )
        }

        viewModelScope.launch {

            try {

                val offset = _uiState.value.feedbackList.size.toLong()

                val newData = repository.getFeedback(
                    limit = pageSize,
                    offset = offset
                )

                val updatedList =
                    _uiState.value.feedbackList + newData

                val summary =
                    repository.getRatingSummary()

                _uiState.update { current ->

                    current.copy(
                        feedbackList = updatedList,
                        ratingAverage = summary.first,
                        ratingDistribution = summary.second,
                        totalReview = updatedList.size,
                        isLoading = false,
                        isLoadingMore = false,
                        endReached = newData.size < pageSize,
                        errorMessage = null
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
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

            _uiState.update {
                it.copy(isSubmitting = true)
            }

            try {

                repository.insertFeedback(
                    FeedbackModel(
                        schoolName = school,
                        parentName = parent,
                        comment = comment,
                        rating = rating
                    )
                )

                _uiState.update {
                    it.copy(isSubmitting = false)
                }

                refresh()

            } catch (e: Exception) {

                e.printStackTrace()

                _uiState.update {
                    it.copy(
                        isSubmitting = false,
                        errorMessage = e.message
                    )
                }
            }

            println("SEND FEEDBACK CALLED")
            println("school=$school parent=$parent rating=$rating comment=$comment")
        }
    }

    fun refresh() {

        viewModelScope.launch {

            try {

                val firstPage = repository.getFeedback(
                    limit = pageSize,
                    offset = 0
                )

                val summary = repository.getRatingSummary()

                withContext(Dispatchers.Main) {

                    _uiState.value = _uiState.value.copy(
                        feedbackList = firstPage,
                        ratingAverage = summary.first,
                        ratingDistribution = summary.second,
                        totalReview = firstPage.size,
                        isRefreshing = false,
                        endReached = firstPage.size < pageSize,
                        errorMessage = null
                    )

                }

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun startRealtimeUpdates() {

        viewModelScope.launch {

            while (true) {

                delay(10000)

                refresh()
            }
        }
    }
}