package com.nicolascristaldo.miniblog.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nicolascristaldo.miniblog.R
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
            label = R.string.text_field_post_label,
            validateInput = { uiState.isValidPost() },
            errorText = R.string.error_post_length,
            singleLine = false,
            maxLines = 4,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.padding_medium),
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
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
                text = stringResource(R.string.button_send),
                fontWeight = FontWeight.Bold
            )
        }
    }
}