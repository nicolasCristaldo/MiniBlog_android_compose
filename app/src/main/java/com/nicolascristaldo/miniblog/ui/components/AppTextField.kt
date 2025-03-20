package com.nicolascristaldo.miniblog.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.R

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    validateInput: () -> Boolean,
    errorText: Int? = null,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
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
            label = { Text(text = stringResource(label)) },
            isError = if (isFirstTime) false else !validateInput(),
            trailingIcon = {
                if (!validateInput() && !isFirstTime) {
                    Icon(
                        imageVector = Icons.Filled.Warning,
                        tint = MaterialTheme.colorScheme.error,
                        contentDescription = null
                    )
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(color = textColor),
            modifier = Modifier.fillMaxWidth()
        )

        if (!validateInput() && !isFirstTime) {
            Text(
                text = stringResource(errorText ?: R.string.error_invalid_input),
                color = MaterialTheme.colorScheme.error,
                fontSize = dimensionResource(R.dimen.text_size_small).value.sp,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.padding_large),
                    top = dimensionResource(R.dimen.padding_small)
                )
            )
        }
    }
}