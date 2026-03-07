package com.example.mbg.core.ui.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        visualTransformation =
            if (isPassword) PasswordVisualTransformation()
            else VisualTransformation.None,
        colors = TextFieldDefaults.colors(

            // background container
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,

            // border / outline
            focusedIndicatorColor = BlueNormal,
            unfocusedIndicatorColor = BlueNormal.copy(alpha = 0.7f),

            // text color
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,

            // cursor
            cursorColor = BlueNormal
        )
    )
}