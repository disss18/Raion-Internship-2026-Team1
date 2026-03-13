package com.example.mbg.feature.reward.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.reward.presentation.component.*
import com.example.mbg.feature.reward.presentation.viewmodel.RewardViewModel
import io.github.jan.supabase.gotrue.auth

@Composable
fun RewardScreen(

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * POINT SUMMARY
         */
        PointSummaryCard(
            totalPoint = uiState.point,
            role = "Pejuang Nutrisi"
        )

        Spacer(modifier = Modifier.height(12.dp))

        /**
         * POINT PROGRESS
         */
        PointProgressCard(
            currentPoint = uiState.point,
            nextLevelPoint = 5000
        )

        Spacer(modifier = Modifier.height(16.dp))

        /**
         * CARA MENDAPATKAN POIN
         */
        SectionHeader(
            title = "Cara Mendapatkan Koin"
        )

        CoinInfoCard()

        Spacer(modifier = Modifier.height(20.dp))

        /**
         * ARTIKEL EDUKASI
         */
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

        Spacer(modifier = Modifier.height(20.dp))

        /**
         * TUKAR POIN
         */
        SectionHeader(
            title = "Tukar Poin",
            actionText = "Lihat Semua"
        )

        val rewards = listOf(

            RewardItem(
                id = "reward1",
                title = "Voucher Rp50.000",
                cost = 5000
            ),

            RewardItem(
                id = "reward2",
                title = "Voucher Rp25.000",
                cost = 2500
            ),

            RewardItem(
                id = "reward3",
                title = "Voucher Rp10.000",
                cost = 1000
            )
        )

        LazyRow(

            contentPadding = PaddingValues(horizontal = 16.dp),

            horizontalArrangement = Arrangement.spacedBy(12.dp)

        ) {

            items(rewards) { reward ->

                RewardCard(

                    title = reward.title,

                    point = reward.cost,

                    userPoint = uiState.point,

                    onRedeemClick = {

                        userId?.let {

                            viewModel.redeem(
                                userId = it,
                                rewardId = reward.id,
                                cost = reward.cost
                            )

                        }

                    }

                )

            }

        }

        Spacer(modifier = Modifier.height(32.dp))

    }

    /**
     * POPUP VOUCHER
     */
    voucherCode?.let { code ->

        AlertDialog(

            onDismissRequest = { voucherCode = null },

            confirmButton = {

                TextButton(
                    onClick = { voucherCode = null }
                ) {
                    Text("Tutup")
                }

            },

            title = {
                Text("Kode Unik Anda")
            },

            text = {

                Column {

                    Text(
                        text = code,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            clipboardManager.setText(
                                AnnotatedString(code)
                            )

                        }

                    ) {

                        Text("Salin Kode Referal")

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Kode ini dapat ditukarkan ke berbagai mitra di shopee untuk mendapatkan hadiah menarik!"
                    )

                }

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