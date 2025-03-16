package com.nicolascristaldo.miniblog.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.domain.model.User
import com.nicolascristaldo.miniblog.ui.screens.home.components.PublishPostSection
import com.nicolascristaldo.miniblog.ui.screens.post.PostListScreen

@Composable
fun HomeScreen(
    user: User?,
    viewModel: HomeViewModel,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        PublishPostSection(
            onPostPublished = { viewModel.publishPost(it) },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = "latest posts",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )

        HorizontalDivider()

        PostListScreen(
            currentUserId = user?.uid ?: "",
            navigateToUserProfile = navigateToUserProfile,
            modifier = Modifier.fillMaxSize()
        )
    }
}