package com.example.mbg.feature.feedback.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.core.ui.component.FeedbackSkeleton
import com.example.mbg.core.util.formatTimeAgo
import com.example.mbg.feature.feedback.component.AllergyItem
import com.example.mbg.feature.feedback.component.FeedbackCard
import com.example.mbg.feature.feedback.component.RatingSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    viewModel: FeedbackViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.refresh()
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3F4F6))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Feedback & Alergi",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                RatingSummaryCard(
                    ratingAverage = state.ratingAverage,
                    totalReview = state.totalReview,
                    ratingDistribution = state.ratingDistribution
                )
            }

            item {
                Text(
                    text = "Daftar Alergi Siswa",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            AllergyItem(
                                title = "Alergi Kacang",
                                totalStudent = 6
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            AllergyItem(
                                title = "Alergi Telur",
                                totalStudent = 5
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            AllergyItem(
                                title = "Vegetarian",
                                totalStudent = 2
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            AllergyItem(
                                title = "Alergi Ayam",
                                totalStudent = 1
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Feedback Terbaru",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            if (state.feedbackList.isEmpty() && state.isLoading) {
                items(3) {
                    FeedbackSkeleton()
                }
            } else {
                items(
                    items = state.feedbackList,
                    key = { feedback -> feedback.id ?: "${feedback.schoolName}-${feedback.createdAt}" }
                ) { feedback ->
                    FeedbackCard(
                        schoolName = feedback.schoolName,
                        parentName = feedback.parentName,
                        comment = feedback.comment,
                        rating = feedback.rating,
                        time = feedback.createdAt?.let(::formatTimeAgo) ?: "-"
                    )
                }
            }

            if (!state.endReached && state.feedbackList.isNotEmpty()) {
                item {
                    LaunchedEffect(state.feedbackList.size) {
                        viewModel.loadFeedback()
                    }

                    if (state.isLoadingMore) {
                        FeedbackSkeleton()
                    }
                }
            }
        }
    }
}