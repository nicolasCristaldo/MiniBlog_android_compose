package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen

@Composable
fun HomeScreen(
    user: User?,
    viewModel: HomeViewModel,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            placeholder = { Text(text = "What's on your mind?") },
            maxLines = 5,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.publishPost(value)
                value = ""
            },
            enabled = value.isNotBlank()
        ) {
            Text(
                text = "Send",
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "latest posts",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )

        PostListScreen(
            currentUserId = user?.uid ?: "",
            navigateToUserProfile = navigateToUserProfile,
            modifier = Modifier.fillMaxSize()
        )
    }
}