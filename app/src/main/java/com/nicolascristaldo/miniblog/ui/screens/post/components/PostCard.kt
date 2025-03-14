package com.nicolascristaldo.miniblog.ui.screens.post.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.domain.model.PostWithUser
import com.nicolascristaldo.miniblog.ui.components.UserImage

@Composable
fun PostCard(
    currentUserId: String,
    postWhitUser: PostWithUser,
    deletePost: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            UserImage(
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navigateToUserProfile(postWhitUser.user.uid) }
            )
            Text(
                text = postWhitUser.user.name,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { navigateToUserProfile(postWhitUser.user.uid) }
            )
            Spacer(modifier = Modifier.weight(1f))
            if(currentUserId == postWhitUser.post.userId){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { deletePost() }
                )
            }
        }
        Text(
            text = postWhitUser.post.content,
            modifier = Modifier.padding(start = 34.dp, end = 16.dp)
        )

        Text(
            text = postWhitUser.post.createdAt.toString(),
            textAlign = TextAlign.End,
            fontSize = 12.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp)
        )
    }
}