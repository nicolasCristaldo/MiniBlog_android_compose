package com.nicolascristaldo.miniblog.ui.screens.post

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.ui.screens.post.components.PostCard

@Composable
fun PostListScreen(
    viewModel: PostViewModel = hiltViewModel(),
    currentUserId: String,
    uid: String? = null,
    navigateToUserProfile: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.loadPosts(uid)
    }
    val postsWhitUser by viewModel.posts.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(postsWhitUser) { postWhitUser ->
            PostCard(
                postWhitUser = postWhitUser,
                currentUserId = currentUserId,
                deletePost = { viewModel.deletePost(postWhitUser.post.id) },
                navigateToUserProfile = navigateToUserProfile,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            HorizontalDivider()
        }
    }
}