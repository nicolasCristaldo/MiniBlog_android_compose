package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.ui.screens.home.components.PublishPostSection
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    viewModel: HomeViewModel,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        PublishPostSection(
            uiState = uiState,
            onPostContentChange = viewModel::changePostContent,
            onPublishPost = viewModel::publishPost,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Latest posts",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .fillMaxWidth()
        )

        HorizontalDivider()

        PostListScreen(
            currentUserId = uiState.user?.uid ?: "",
            navigateToUserProfile = navigateToUserProfile,
            modifier = Modifier.fillMaxSize()
        )
    }
}