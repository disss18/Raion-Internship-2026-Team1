package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mbg.feature.reward.presentation.screen.RewardItem

@Composable
fun RewardGrid(

    userPoint: Int,

    onRedeem: (String, Int) -> Unit

) {

    val rewards = listOf(

        RewardItem("reward1","Voucher Rp100.000",10000),
        RewardItem("reward2","Voucher Rp75.000",7500),
        RewardItem("reward3","Voucher Rp50.000",5000),
        RewardItem("reward4","Voucher Rp25.000",2500)

    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        rewards.chunked(2).forEach { rowItems ->

            Row(

                horizontalArrangement = Arrangement.spacedBy(12.dp),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)

            ) {

                rowItems.forEach { reward ->

                    Box(
                        modifier = Modifier.weight(1f)
                    ) {

                        RewardCard(

                            title = reward.title,

                            point = reward.cost,

                            userPoint = userPoint,

                            onRedeemClick = {

                                onRedeem(
                                    reward.id,
                                    reward.cost
                                )

                            }

                        )

                    }

                }

            }

        }

    }

}