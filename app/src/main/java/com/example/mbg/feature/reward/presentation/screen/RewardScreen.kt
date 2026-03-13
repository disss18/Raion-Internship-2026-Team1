package com.example.mbg.feature.reward.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.core.ui.component.DashboardBottomBar
import com.example.mbg.feature.reward.presentation.component.*
import com.example.mbg.feature.reward.presentation.viewmodel.RewardViewModel
import io.github.jan.supabase.gotrue.auth

@Composable
fun RewardScreen(

    navController: NavController,

    viewModel: RewardViewModel = viewModel()

) {

    val uiState by viewModel.uiState.collectAsState()

    val userId = SupabaseClientProvider.client.auth.currentUserOrNull()?.id

    val clipboardManager = LocalClipboardManager.current

    var voucherCode by remember { mutableStateOf<String?>(null) }

    /**
     * LOAD DATA
     */
    LaunchedEffect(userId) {

        userId?.let {

            viewModel.loadPoint(it)

            viewModel.startRealtimeListener(it)

        }

    }

    /**
     * OBSERVE VOUCHER
     */
    LaunchedEffect(uiState.voucherCode) {

        voucherCode = uiState.voucherCode

    }

    Scaffold(

        bottomBar = {

            DashboardBottomBar(
                navController = navController
            )

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            RewardHeader()

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = (-16).dp)
                    .background(
                        Color(0xFFF4F4F4),
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp
                        )
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp)

            ) {

                PointSummaryCard(
                    totalPoint = uiState.point,
                    role = "Pejuang Nutrisi"
                )

                Spacer(Modifier.height(20.dp))

                SectionHeader(
                    title = "Cara Mendapatkan Koin"
                )

                CoinInfoCard()

                Spacer(Modifier.height(20.dp))

                SectionHeader(
                    title = "Artikel Edukasi Terbaru",
                    actionText = "Lihat Semua"
                )

                ArticleCard(
                    onClick = {
                        userId?.let {
                            viewModel.addArticlePoint(it)
                        }
                    }
                )

                Spacer(Modifier.height(20.dp))

                SectionHeader(
                    title = "Tukar Poin",
                    actionText = "Lihat Semua"
                )

                RewardGrid(
                    userPoint = uiState.point,
                    onRedeem = { rewardId, cost ->

                        userId?.let {

                            viewModel.redeem(
                                userId = it,
                                rewardId = rewardId,
                                cost = cost
                            )

                        }

                    }
                )

                Spacer(Modifier.height(40.dp))

            }

        }

    }

    /**
     * POPUP VOUCHER SUCCESS
     */
    voucherCode?.let { code ->

        RedeemDialog(

            code = code,

            onCopy = {

                clipboardManager.setText(
                    AnnotatedString(code)
                )

            },

            onDismiss = {

                voucherCode = null

            }

        )

    }

    /**
     * POPUP POINT NOT ENOUGH
     */
    if (uiState.notEnoughPoint) {

        PointNotEnoughDialog(

            onDismiss = {

                viewModel.resetNotEnoughPoint()

            }

        )

    }

}

/**
 * SIMPLE UI MODEL
 */
data class RewardItem(

    val id: String,

    val title: String,

    val cost: Int

)