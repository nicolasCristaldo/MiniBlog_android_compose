package com.nicolascristaldo.miniblog.ui.screens.post.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascristaldo.miniblog.domain.model.PostWithUser
import com.nicolascristaldo.miniblog.ui.components.UserImage

@Composable
fun PostCard(
    currentUserId: String,
    postWhitUser: PostWithUser,
    onDeletePost: () -> Unit,
    navigateToUserProfile: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        UserImage(
            modifier = Modifier
                .size(34.dp)
                .clickable { navigateToUserProfile(postWhitUser.user.uid) }
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(34.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = postWhitUser.user.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
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
                            .clickable { onDeletePost() }
                    )
                }
            }
            Text(
                text = postWhitUser.post.content
            )
            Text(
                text = postWhitUser.post.createdAt.toString(),
                textAlign = TextAlign.End,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}