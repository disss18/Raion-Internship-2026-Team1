package com.example.mbg.core.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardSectionTitle(
    title: String,
    action: String? = null,
    onActionClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        if (action != null && onActionClick != null) {

            TextButton(
                onClick = { onActionClick() }
            ) {
                Text(action)
            }
        }
    }
}