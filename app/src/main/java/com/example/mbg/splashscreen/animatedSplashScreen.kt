package com.example.mbg.splashscreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.mbg.R

@Composable
fun AnimatedSplashScreen(
    onNavigateToOnboarding: () -> Unit
) {

    var step by remember { mutableIntStateOf(1) }

    // ================= Navigation Trigger =================
    LaunchedEffect(Unit) {
        delay(300)
        step = 2
        delay(1000)
        step = 3
        delay(500)
        step = 4
        delay(800)

        onNavigateToOnboarding()
    }

    // ================= Animations =================
    val offsetX by animateDpAsState(
        targetValue = if (step == 2) (-55).dp else 0.dp,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = ""
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (step == 2) 1f else 0f,
        animationSpec = tween(400),
        label = ""
    )

    val boxSize by animateDpAsState(
        targetValue = if (step >= 4) 4000.dp else 80.dp,
        animationSpec = tween(1500, easing = FastOutSlowInEasing),
        label = ""
    )

    val cornerRadius by animateDpAsState(
        targetValue = if (step >= 4) 500.dp else 20.dp,
        animationSpec = tween(800),
        label = ""
    )

    val iconAlpha by animateFloatAsState(
        targetValue = if (step >= 4) 0f else 1f,
        animationSpec = tween(400),
        label = ""
    )

    // ================= UI =================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "MBG +",
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF83E0D4),
            modifier = Modifier
                .offset(x = 65.dp)
                .alpha(textAlpha)
        )

        Box(
            modifier = Modifier
                .offset(x = offsetX)
                .requiredSize(boxSize)
                .clip(RoundedCornerShape(cornerRadius))
                .background(Color(0xFF83E0D4)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_mbg),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .alpha(iconAlpha)
            )
        }
    }
}