package com.example.mbg.feature.reward.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.reward.data.repository.RewardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RewardUiState(

    val point: Int = 0,
    val voucherCode: String? = null,
    val isLoading: Boolean = false,
    val notEnoughPoint: Boolean = false
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

            val result = repository.redeemReward(
                userId,
                rewardId,
                cost
            )

            result.onSuccess { code ->

                _uiState.update {

                    it.copy(
                        voucherCode = code,
                        notEnoughPoint = false
                    )
                }

            }.onFailure { error ->

                if (error.message == "POINT_NOT_ENOUGH") {

                    _uiState.update {

                        it.copy(
                            notEnoughPoint = true
                        )
                    }
                }
            }
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

    fun resetNotEnoughPoint() {

        _uiState.update {

            it.copy(
                notEnoughPoint = false
            )
        }
    }
}