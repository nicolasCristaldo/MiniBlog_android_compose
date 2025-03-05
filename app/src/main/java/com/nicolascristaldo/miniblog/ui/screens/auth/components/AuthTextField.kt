package com.nicolascristaldo.miniblog.ui.screens.auth.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    validateInput: (String) -> Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    var isValidValue by remember { mutableStateOf(true) }

    OutlinedTextField(
        keyboardOptions = keyboardOptions,
        value = value,
        onValueChange = {
            onValueChange(it)
            isValidValue = validateInput(it)
        },
        label = { Text(text = label) },
        isError = !isValidValue,
        singleLine = true,
        visualTransformation = visualTransformation,
        modifier = modifier
            .width(300.dp)
    )
}