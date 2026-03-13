package com.example.mbg.feature.reward.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.reward.data.repository.RewardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RewardUiState(

    val point: Int = 0,
    val voucherCode: String? = null,
    val isLoading: Boolean = false
)

class RewardViewModel(
    private val repository: RewardRepository = RewardRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RewardUiState())
    val uiState: StateFlow<RewardUiState> = _uiState

    fun loadPoint(userId: String) {

        viewModelScope.launch {

            val point = repository.getUserPoint(userId)

            _uiState.value = _uiState.value.copy(
                point = point
            )
        }
    }

    fun redeem(
        userId: String,
        rewardId: String,
        cost: Int
    ) {

        viewModelScope.launch {

            val code = repository.redeemReward(
                userId = userId,
                rewardId = rewardId,
                pointCost = cost
            )

            _uiState.value = _uiState.value.copy(
                voucherCode = code
            )

            loadPoint(userId)
        }
    }

    fun startRealtimeListener(userId: String) {

        viewModelScope.launch {

            repository.listenUserPoint(userId) { point ->

                _uiState.value = _uiState.value.copy(
                    point = point
                )

            }
        }
    }

    fun addArticlePoint(userId: String) {

        viewModelScope.launch {

            repository.addPointIfNotToday(
                userId = userId,
                activityType = "READ_ARTICLE",
                point = 5
            )

            loadPoint(userId)
        }
    }

    fun addDashboardPoint(userId: String) {

        viewModelScope.launch {

            repository.addPointIfNotToday(
                userId = userId,
                activityType = "OPEN_DASHBOARD",
                point = 10
            )

            loadPoint(userId)
        }
    }
}