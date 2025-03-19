package com.nicolascristaldo.miniblog.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicolascristaldo.miniblog.ui.components.AppTextField
import com.nicolascristaldo.miniblog.ui.screens.home.HomeUiState

@Composable
fun PublishPostSection(
    uiState: HomeUiState,
    onPostContentChange: (String) -> Unit,
    onPublishPost: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AppTextField(
            value = uiState.postContent,
            onValueChange = { onPostContentChange(it) },
            label = "Publish a post",
            validateInput = { uiState.isValidPost() },
            errorText = "Post must be less than 200 characters",
            singleLine = false,
            maxLines = 4,
            modifier = Modifier
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                onPublishPost()
                onPostContentChange("")
            },
            enabled = uiState.isValidPost() && uiState.postContent.isNotBlank()
        ) {
            Text(
                text = "Send",
                fontWeight = FontWeight.Bold
            )
        }
    }
}