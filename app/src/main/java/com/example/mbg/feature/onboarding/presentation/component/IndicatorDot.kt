package com.example.mbg.feature.onboarding.presentation.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.ui.theme.FoundationBlue

@Composable
fun IndicatorDot(
    isSelected: Boolean,
    selectedColor: Color = FoundationBlue,
    unselectedColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
) {

    val width by animateDpAsState(
        targetValue = if (isSelected) 24.dp else 8.dp,
        label = ""
    )

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .height(8.dp)
            .width(width)
            .clip(RoundedCornerShape(50))
            .background(
                if (isSelected) selectedColor else unselectedColor
            )
    )
}