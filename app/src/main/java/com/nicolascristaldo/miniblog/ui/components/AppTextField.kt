package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    validateInput: () -> Boolean,
    errorText: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    var isFirstTime by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            keyboardOptions = keyboardOptions,
            value = value,
            onValueChange = {
                onValueChange(it)
                if (isFirstTime) isFirstTime = false
            },
            label = { Text(text = label) },
            isError = if(isFirstTime) false else !validateInput(),
            trailingIcon = {
                if (!validateInput() && !isFirstTime) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        contentDescription = null
                    )
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth()
        )

        if (!validateInput() && !isFirstTime) {
            Text(
                text = errorText ?: "Invalid input",
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}