package com.example.mbg.feature.reward.presentation.component

import android.R.color.white
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun PointNotEnoughDialog(

    onDismiss: () -> Unit

) {

    Dialog(onDismissRequest = onDismiss) {

        Card(

            shape = RoundedCornerShape(20.dp),

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {

            Column(

                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier
                    .padding(24.dp)

            ) {

                /**
                 * ICON
                 */
                Box(

                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            Color(0xFFE6F2EA),
                            CircleShape
                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Text(
                        text = "!",
                        fontSize = 28.sp,
                        color = Color(0xFF4C8F65)
                    )

                }

                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * TITLE
                 */
                Text(
                    text = "Poin Tidak Cukup",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                /**
                 * DESCRIPTION
                 */
                Text(
                    text = "Poin Anda belum mencukupi untuk menukar hadiah ini.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                /**
                 * BUTTON
                 */
                Button(

                    onClick = onDismiss,

                    modifier = Modifier
                        .fillMaxWidth(),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4C8F65)
                    ),

                    shape = RoundedCornerShape(10.dp)

                ) {

                    Text(
                        text = "OK",
                        color = Color.White
                    )

                }

            }

        }

    }

}