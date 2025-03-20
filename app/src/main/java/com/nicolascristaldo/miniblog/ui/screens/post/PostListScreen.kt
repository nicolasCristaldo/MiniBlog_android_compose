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
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nicolascristaldo.miniblog.R
import com.nicolascristaldo.miniblog.ui.components.AppAlertDialog
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
        viewModel.listenPostsChanges(uid)
    }

    val postsWhitUser by viewModel.posts.collectAsState()
    val postToDelete by viewModel.postToDelete.collectAsState()

    LazyColumn(
        modifier = modifier
    ) {
        items(postsWhitUser) { postWhitUser ->
            PostCard(
                postWhitUser = postWhitUser,
                currentUserId = currentUserId,
                onDeletePost = { viewModel.changePostToDeleteValue(postWhitUser.post.id) },
                navigateToUserProfile = navigateToUserProfile,
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_medium),
                        horizontal = dimensionResource(R.dimen.padding_large)
                    )
                    .fillMaxWidth()
            )
            HorizontalDivider()
        }
    }

    if(postToDelete != null) {
        AppAlertDialog(
            title = R.string.dialog_delete_title,
            content = R.string.dialog_delete_content,
            confirmText = R.string.dialog_delete_confirm,
            onConfirm = { viewModel.deletePost() },
            onCancel = { viewModel.changePostToDeleteValue() },
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}