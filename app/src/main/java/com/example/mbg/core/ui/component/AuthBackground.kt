package com.example.mbg.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    topSpacing: Dp = 150.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        // Wave Background
        Image(
            painter = painterResource(id = R.drawable.background_wave),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (70.dp))
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillWidth
        )

        // Content Area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topSpacing),
        ) {
            content()
        }
    }
}