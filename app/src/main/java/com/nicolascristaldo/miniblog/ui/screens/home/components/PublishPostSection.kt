package com.nicolascristaldo.miniblog.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PublishPostSection(
    onPostPublished: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = {
                Text(
                    text = "What's on your mind?",
                    color = MaterialTheme.colorScheme.secondary
                )
            },
            maxLines = 4,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                onPostPublished(value)
                value = ""
            },
            enabled = value.isNotBlank()
        ) {
            Text(
                text = "Send",
                fontWeight = FontWeight.Bold
            )
        }
    }
}