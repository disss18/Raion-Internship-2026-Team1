package com.example.mbg.feature.auth.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthDivider(
    text: String = "atau",
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
            thickness = 2.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            thickness = 2.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f)
        )
    }
}