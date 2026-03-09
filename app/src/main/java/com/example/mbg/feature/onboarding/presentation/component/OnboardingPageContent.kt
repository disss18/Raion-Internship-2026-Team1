package com.example.mbg.feature.onboarding.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mbg.feature.onboarding.presentation.OnboardingPage

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    pageOffset: Float
) {

    val fraction = 1f - pageOffset.coerceIn(0f, 1f)
    val scale = 0.9f + (1f - 0.9f) * fraction
    val alpha = 0.5f + (1f - 0.5f) * fraction

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // ================= IMAGE AREA =================
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .aspectRatio(1f)
                    .background(
                        color = Color(0xFFE5F0E8),
                        shape = CircleShape
                    )
            )

            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }

        // ================= TEXT AREA =================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = page.title,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}