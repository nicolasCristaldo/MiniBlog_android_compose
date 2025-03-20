package com.nicolascristaldo.miniblog.ui.screens.auth.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nicolascristaldo.miniblog.ui.theme.dark_blue
import com.nicolascristaldo.miniblog.ui.theme.disabled_container
import com.nicolascristaldo.miniblog.ui.theme.disabled_content
import com.nicolascristaldo.miniblog.ui.theme.light_blue

@Composable
fun AuthButton(
    onClick: () -> Unit,
    text: Int,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = light_blue,
            contentColor = dark_blue,
            disabledContentColor = disabled_content,
            disabledContainerColor = disabled_container
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = stringResource(text),
            fontWeight = FontWeight.Bold
        )
    }
}