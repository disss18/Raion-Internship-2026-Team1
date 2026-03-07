package com.example.mbg.feature.role.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import com.example.mbg.core.ui.component.SelectableRoleCard

@Composable
fun RoleItemAnimated(
    title: String,
    description: String,
    icon: Painter,
    selected: Boolean,
    onClick: () -> Unit
) {

    val scale = animateFloatAsState(
        targetValue = if (selected) 1.03f else 1f,
        animationSpec = tween(200),
        label = ""
    )

    Box(
        modifier = Modifier.graphicsLayer {
            scaleX = scale.value
            scaleY = scale.value
        }
    ) {
        SelectableRoleCard(
            title = title,
            description = description,
            icon = icon,
            selected = selected,
            onClick = onClick
        )
    }
}