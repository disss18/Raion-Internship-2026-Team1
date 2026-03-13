package com.example.mbg.feature.reward.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mbg.R

@Composable
fun RedeemDialog(

    code: String,

    onCopy: () -> Unit,

    onDismiss: () -> Unit

) {

    Dialog(onDismissRequest = onDismiss) {

        Card(

            shape = RoundedCornerShape(20.dp),

            modifier = Modifier
                .fillMaxWidth(),

            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier.padding(20.dp)

            ) {

                Icon(
                    painter = painterResource(R.drawable.voucher),
                    contentDescription = null,
                    tint = Color(0xFF4C8F65),
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Kode Unik Anda",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFEAF4EE),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),

                    contentAlignment = Alignment.Center

                ) {

                    Text(
                        text = code,
                        style = MaterialTheme.typography.bodyLarge
                    )

                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(

                    onClick = onCopy,

                    modifier = Modifier.fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4C8F65)
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text(
                        text = "Salin Kode Referal",
                        color = Color.White
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(

                    text = "Kode ini dapat ditukarkan ke berbagai mitra di shopee untuk mendapatkan hadiah menarik!",

                    color = Color.Gray,

                    style = MaterialTheme.typography.bodySmall

                )

            }

        }

    }

}