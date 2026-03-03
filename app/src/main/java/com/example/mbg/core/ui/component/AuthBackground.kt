package com.example.mbg.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mbg.R

@Composable
fun AuthBackground(
    modifier: Modifier = Modifier,
    waveOffsetY: Dp = 0.dp,          // ⬅ kontrol naik turun
    waveHeightOffset: Float = 1f     // ⬅ kontrol scale kalau mau
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Image(
            painter = painterResource(id = R.drawable.background_wave),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = waveOffsetY) // ⬅ ini yang bikin bisa naik turun
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillWidth
        )
    }
}